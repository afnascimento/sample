package com.unilever.julia.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unilever.julia.AppApplication
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.CustomerDeserializer
import com.unilever.julia.data.model.TerritoryDeserializer
import java.lang.RuntimeException

class Worker31PreInicio(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val imeiDevice = "imeiDevice"
        const val tokenFirebase = "tokenFirebase"
        const val nameUser = "nameUser"
    }

    private val mGson: Gson = GsonBuilder()
        .registerTypeAdapter(Customer::class.java, CustomerDeserializer())
        .registerTypeAdapter(Territory::class.java, TerritoryDeserializer())
        .create()

    private val mDataManager: DataManager = (applicationContext as AppApplication).dataManager

    override fun doWork(): Result {
        val imeiDevice : String = inputData.getString(imeiDevice) ?: ""
        val tokenFirebase : String = inputData.getString(tokenFirebase) ?: ""
        //val nameUser : String = inputData.getString(nameUser) ?: ""

        try {
            val call = mDataManager.juliaApi().send31PreInicio(imeiDevice, tokenFirebase)

            val body = call.execute().body() ?: throw RuntimeException()
            val conversations : Conversations = body

            val jsonParser = conversations.getAnswer().technicalText ?: ""

            val jsonElement : JsonElement = mGson.fromJson(jsonParser, JsonElement::class.java)

            val jsonObject : JsonObject? = jsonElement.asJsonObject

            var customers : MutableList<Customer> = arrayListOf()
            var territories : MutableList<Territory> = arrayListOf()
            if (jsonObject != null) {
                if (jsonObject.has("customers")) {
                    val json = jsonObject.getAsJsonArray("customers")
                    customers = mGson.fromJson(json, Array<Customer>::class.java).toMutableList()
                }
                if (jsonObject.has("territories")) {
                    val json = jsonObject.getAsJsonArray("territories")
                    territories = mGson.fromJson(json, Array<Territory>::class.java).toMutableList()
                }
            }

            if (customers.isEmpty()) {
                mDataManager.database().customerDao().deleteAllCustomers()
            } else {
                mDataManager.database().customerDao().insertCustomers(customers)
            }
            if (territories.isEmpty()) {
                mDataManager.database().territoryDao().deleteAllTerritories()
            } else {
                mDataManager.database().territoryDao().insertTerritories(territories)
            }

            return Result.success()
        } catch (e : Throwable) {
            return Result.failure()
        }
    }
}