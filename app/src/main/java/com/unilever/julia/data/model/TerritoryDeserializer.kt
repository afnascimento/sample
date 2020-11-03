package com.unilever.julia.data.model

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.unilever.julia.data.database.entity.Territory
import java.lang.reflect.Type

class TerritoryDeserializer : JsonDeserializer<Territory> {

    private val mGson: Gson = Gson()

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Territory {
        val code : String = json?.asString ?: ""

        val territory = Territory()
        territory.code = code

        return territory
    }
}