package com.unilever.julia.utils

import com.unilever.julia.data.model.AgendaModel
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.apache.commons.lang3.StringUtils

class AgendaModelParser {

    companion object {

        @JvmStatic
        private var mGson: Gson = Gson()

        fun parserToObject(json : String): Array<AgendaModel.Item> {
            try {
                val jsonArray = JsonArray()

                val objects = StringUtils.substringsBetween(json, "{", "}")

                for (propertie in objects) {

                    val jsonObject = JsonObject()

                    val entreVirgulas = StringUtils.split(propertie, "\"," )

                    val searchList : Array<String> = arrayOf("\"", "\n")
                    val replacementList : Array<String> = arrayOf("", "")

                    for (entreVirgulaItem in entreVirgulas) {

                        val entrePontos = StringUtils.split(entreVirgulaItem, ":" )

                        val name : String = StringUtils.replaceEach(entrePontos[0], searchList, replacementList)

                        var value = StringUtils.replaceEach(entrePontos[1], searchList, replacementList)
                        if (StringUtils.containsIgnoreCase(value, "null")) {
                            value = ""
                        }

                        jsonObject.addProperty(StringUtils.trimToEmpty(name), StringUtils.trimToEmpty(value))
                    }

                    jsonArray.add(jsonObject)
                }

                return mGson.fromJson(jsonArray.toString(), Array<AgendaModel.Item>::class.java)
            } catch (e : Throwable) {
                return emptyArray()
            }
        }
    }
}