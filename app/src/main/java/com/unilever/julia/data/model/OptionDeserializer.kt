package com.unilever.julia.data.model

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class OptionDeserializer : JsonDeserializer<Option> {

    private val mGson: Gson = Gson()

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Option {
        val model = mGson.fromJson(json?.asJsonObject, Option::class.java)
        return model
    }

}
