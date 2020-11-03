package com.unilever.julia

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.unilever.julia.data.model.ipv.IpvModel
import com.unilever.julia.utils.Utils
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

class IpvModelParserTest {

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
    fun parseJson_test() {
        val json = "{\\n              \\\"ipv\\\": {\\n                            \\\"target\\\": {\\n                                           \\\"percentage\\\": 88.00\\n                            },\\n                            \\\"score\\\": {\\n                                           \\\"percentage\\\": 77.00,\\n                                           \\\"colorCode\\\": \\\"#78be20\\\"\\n                            },\\n                            \\\"phasing\\\": {\\n                                           \\\"percentage\\\": 17.60,\\n                                           \\\"colorCode\\\": \\\"#da2c27\\\",\\\"type\\\":\\\"Faseamento\\\"\\n                            },\\n                            \\\"innovation\\\": {\\n                                           \\\"percentage\\\": 7.60,\\n                                           \\\"colorCode\\\": \\\"#da2c27\\\",\\\"type\\\":\\\"Inovação\\\"\\n                            },\\n                            \\\"positivation\\\": {\\n                                           \\\"percentage\\\": 57.60,\\n                                           \\\"colorCode\\\": \\\"#f0b331\\\",\\\"type\\\":\\\"Positivação\\\"\\n                            },\\n                            \\\"offers\\\": {\\n                                           \\\"percentage\\\": 77.60,\\n                                           \\\"colorCode\\\": \\\"#78be20\\\",\\\"type\\\":\\\"Ofertas\\\"\\n                            },\\n                            \\\"priority\\\": {\\n                                           \\\"percentage\\\": 100.00,\\n                                           \\\"colorCode\\\": \\\"#78be20\\\",\\\"type\\\":\\\"Prioridade\\\"\\n                            }\\n              }\\n}\\n"
        val fromJson = mGson.fromJson(Utils.removeCharactersInStringJson(json), JsonElement::class.java)

        val ipv : JsonElement = fromJson.asJsonObject.get("ipv")
        val jsonIpv = mGson.toJson(ipv)
        System.out.println(jsonIpv)

        val model = mGson.fromJson(jsonIpv, IpvModel::class.java)
        Assert.assertNotNull(model)
    }
}