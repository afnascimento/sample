package com.unilever.julia

import com.google.gson.*
import com.unilever.julia.data.model.ContextRequest
import com.unilever.julia.utils.ParsePatternsEnums
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.parseDate
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test

class UtilsTest {

    companion object {
        @JvmStatic
        private lateinit var mGson: Gson

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mGson = GsonBuilder()
                .registerTypeAdapter(ContextRequest::class.java, ContextRequest.Serializer())
                .serializeNulls()
                .setPrettyPrinting()
                .create()
        }
    }

    @Ignore
    @Test
    fun check_isInteger() {
        Assert.assertEquals(Utils.isInteger(2.08), false)
        Assert.assertEquals(Utils.isInteger(2.0), true)
    }

    @Test
    fun parseContextRequest_test() {
        val request = ContextRequest()
            .addItem("code", "03_INOVACAO_INFO")
            .setNameContext("context")
            .addContextItem("categoria", "")
            .addContextItem("commodity", "HOME CARE")
            .addContextItem("marcas", "")
            .addContextItem("projeto", "")
            .addContextItem("dataLancamento", "")

        val json = mGson.toJson(request)

        val parser = JsonParser()
        val jsonTarget : JsonElement = parser.parse(json)
        val jsonSource = parser.parse(
            "{\n" +
                    "    \"code\": \"03_INOVACAO_INFO\",\n" +
                    "    \"context\": {\n" +
                    "        \"categoria\": \"\",\n" +
                    "        \"commodity\": \"HOME CARE\",\n" +
                    "        \"marcas\": \"\",\n" +
                    "        \"projeto\": \"\",\n" +
                    "        \"dataLancamento\": \"\"\n" +
                    "    }\n" +
                    "}"
        )

        Assert.assertEquals(jsonSource, jsonTarget)
    }

    @Ignore
    @Test
    fun parseDate_test() {
        val date = "2019-01-08 23:10:59.128".parseDate(ParsePatternsEnums.YYYYMMDD_HHMMSS_SSS)
        Assert.assertNotNull(date)
    }
}