package com.unilever.julia

import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import com.google.gson.*
import com.unilever.julia.data.model.bulletin.AttendanceResponse

class AttendanceParserTest {

    companion object {
        @JvmStatic
        private lateinit var mGson: Gson

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mGson = Gson()
        }
    }

    @Test
    fun parserJson_test() {
        val json = "{\n" +
                "  \"territory\": {\n" +
                "    \"100035\": \"ANDERSON DA CONCEICAO MACHADO\",\n" +
                "\t\"100036\": \"ARNALDO MOREIRA DO NASCIMENTO JUNIO\"\n" +
                "  },\n" +
                "  \"district\": {\n" +
                "    \"99\": \"ROSELI AGUIAR DALLE MOLLE DE SOUSA\",\n" +
                "\t\"534\": \"DANNILO PEREIRA LOPES\"\n" +
                "  },\n" +
                "  \"hitRegional\": [\n" +
                "    \"SP\"\n" +
                "  ],\n" +
                "  \"subsidiary\": [\n" +
                "    \"ROSANDRO TIBIRICA ROSANO\"\n" +
                "  ]\n" +
                "}"
        val fromJson = mGson.fromJson(json, AttendanceResponse::class.java)
        Assert.assertNotNull(fromJson)
    }
}