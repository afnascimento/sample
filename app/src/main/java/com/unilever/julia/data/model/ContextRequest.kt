package com.unilever.julia.data.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class ContextRequest {

    private var name : String = "context"

    private val items = linkedMapOf<String, Any>()

    private val context = linkedMapOf<String, Any>()

    fun setNameContext(name: String): ContextRequest {
        this.name = name
        return this
    }

    fun addItem(field: String, value: Any): ContextRequest {
        items[field] = value
        return this
    }

    fun addContextItem(field: String, value: Any): ContextRequest {
        context[field] = value
        return this
    }

    class Serializer : JsonSerializer<ContextRequest> {
        override fun serialize(
            request: ContextRequest?,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            val result = JsonObject()
            if (request != null) {

                for (it in request.items) {
                    setValueByType(result, it.key, it.value)
                }
                if (request.context.isNotEmpty()) {
                    val child = JsonObject()
                    for (it in request.context) {
                        setValueByType(child, it.key, it.value)
                    }
                    result.add(request.name, child)
                }
            }
            return result
        }

        private fun setValueByType(jsonObject : JsonObject, key : String, value : Any) {
            if (value is String) {
                jsonObject.addProperty(key, value)
                return
            }
            if (value is Int) {
                jsonObject.addProperty(key, value)
                return
            }
            if (value is Float) {
                jsonObject.addProperty(key, value)
                return
            }
            if (value is Double) {
                jsonObject.addProperty(key, value)
                return
            }
            if (value is Boolean) {
                jsonObject.addProperty(key, value)
                return
            }
        }
    }
}