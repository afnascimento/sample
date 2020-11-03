package com.unilever.julia

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.model.CustomerDeserializer
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class CustomerParserParserTest {

    companion object {
        @JvmStatic
        private lateinit var mGson: Gson

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mGson = GsonBuilder()
                .registerTypeAdapter(Customer::class.java, CustomerDeserializer())
                .create()
        }
    }

    @Test
    fun parseJson_test() {
        val jsonParser = "{\n" +
                "  \"customers\": [\n" +
                "    {\n" +
                "      \"id\": 10037081,\n" +
                "      \"territory\": \"000370\",\n" +
                "      \"name\": \"SERVIMED COML LTDA\",\n" +
                "      \"state\": \"SP\",\n" +
                "      \"city\": \"BAURU\",\n" +
                "      \"district\": \"JARDIM CONTORNO\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 10770916,\n" +
                "      \"territory\": \"000370\",\n" +
                "      \"name\": \"SERVIMED COML LTDA\",\n" +
                "      \"state\": \"MG\",\n" +
                "      \"city\": \"RIBEIRAO DAS NEVES\",\n" +
                "      \"district\": \"JARDIM COLONIAL\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"territories\": [\n" +
                "    \"001030\",\n" +
                "    \"006027\",\n" +
                "    \"006028\",\n" +
                "    \"009999\",\n" +
                "    \"905032\"\n" +
                "  ]\n" +
                "}"
        val jsonElement : JsonElement = mGson.fromJson(jsonParser, JsonElement::class.java)

        val jsonObject : JsonObject? = jsonElement.asJsonObject

        var customers : MutableList<Customer> = arrayListOf()
        var territories : MutableList<String> = arrayListOf()
        if (jsonObject != null) {
            if (jsonObject.has("customers")) {
                val json = jsonObject.getAsJsonArray("customers")
                customers = mGson.fromJson(json, Array<Customer>::class.java).toMutableList()
            }
            if (jsonObject.has("territories")) {
                val json = jsonObject.getAsJsonArray("territories")
                territories = mGson.fromJson(json, Array<String>::class.java).toMutableList()
            }
        }
        Assert.assertNotNull(customers.isNotEmpty() && territories.isNotEmpty())
    }
}