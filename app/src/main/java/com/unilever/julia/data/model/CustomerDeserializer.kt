package com.unilever.julia.data.model

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.lang.reflect.Type

class CustomerDeserializer : JsonDeserializer<Customer> {

    private val mGson: Gson = Gson()

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Customer {
        val customerModel = mGson.fromJson(json?.asJsonObject, CustomerModel::class.java)

        val customer = Customer()
        customer.code = customerModel.id
        customer.territory = customerModel.territory
        customer.name = customerModel.name
        customer.state = customerModel.state
        customer.city = customerModel.city
        customer.district = customerModel.district

        val nameFilter : String = Utils.removeAccents(customer.getNameWithCode())
        val addressFilter : String = Utils.removeAccents(customer.getAddress())
        val textToFilter = StringUtils.upperCase("$nameFilter - $addressFilter")
        customer.textToFilter = textToFilter

        return customer
    }
}