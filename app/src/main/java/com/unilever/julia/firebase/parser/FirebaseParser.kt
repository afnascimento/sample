package com.unilever.julia.firebase.parser

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.unilever.julia.logger.Logger
import com.unilever.julia.utils.getJsonObject
import com.unilever.julia.utils.getString
import org.apache.commons.lang3.StringUtils

class FirebaseParser {

    companion object {
        private const val TAG = "FirebaseCloudMessaging"

        private const val KEY_TYPE = "type"
        private const val KEY_VALUE = "value"
        private const val KEY_CONTEXT = "context"

        private const val KEY_TITLE = "title"
        private const val KEY_BODY = "body"

        private const val KEY_ICON = "icon"
        private const val KEY_COLOR = "color"

        private const val KEY_PARAM = "param"
        private const val KEY_ID = "id"
    }

    private fun normalizerJsonObject(data : Map<String, String>): JsonObject {
        val jsonObject = JsonObject()
        for (it in data.entries) {
            val isJsonObject = StringUtils.contains(it.value, "{") && StringUtils.contains(it.value, "}")
            if (isJsonObject) {
                jsonObject.add(it.key, JsonParser().parse(it.value))
            } else {
                jsonObject.addProperty(it.key, it.value)
            }
        }
        return jsonObject
    }

    fun parser(data : Map<String, String>): FirebaseNotification? {
        return try {
            val jsonObject = normalizerJsonObject(data)
            return parser(jsonObject)
        } catch (e : Throwable) {
            e.printStackTrace()
            null
        }
    }

    fun parser(jsonObject: JsonObject): FirebaseNotification? {
        try {
            Logger.debug(TAG, jsonObject.toString())

            val action : JsonObject = jsonObject.getJsonObject("action")
                ?: return getActionNoneNotification(jsonObject, JsonObject())

            val type : FirebaseNotification.Type? = FirebaseNotification.Type.getByKey(
                action.getString(KEY_TYPE) ?: ""
            )
            return when (type) {
                FirebaseNotification.Type.NONE -> getActionNoneNotification(jsonObject, action)
                FirebaseNotification.Type.TEXT -> getActionTextNotification(jsonObject, action)
                FirebaseNotification.Type.CODE -> getActionCodeNotification(jsonObject, action)
                else -> null
            }
        } catch (e : Throwable) {
            e.printStackTrace()
            return null
        }
    }

    private fun getActionNoneNotification(data: JsonObject, action : JsonObject): FirebaseNotification {
        return ActionNoneNotification(
            data.getString(KEY_TITLE) ?: "",
            data.getString(KEY_BODY) ?: "",
            data.getString(KEY_COLOR) ?: "",
            data.getString(KEY_ICON) ?: "",
            data.getString(KEY_PARAM) ?: "",
            action.getString(KEY_ID) ?: ""
        )
    }

    private fun getActionTextNotification(data: JsonObject, action : JsonObject): FirebaseNotification {
        return ActionTextNotification(
            data.getString(KEY_TITLE) ?: "",
            data.getString(KEY_BODY) ?: "",
            data.getString(KEY_COLOR) ?: "",
            data.getString(KEY_ICON) ?: "",
            data.getString(KEY_PARAM) ?: "",
            action.getString(KEY_ID) ?: "",
            action.getString(KEY_VALUE) ?: ""
        )
    }

    private fun getActionCodeNotification(data: JsonObject, action : JsonObject): FirebaseNotification {
        val context : JsonObject = action.getJsonObject(KEY_CONTEXT)
            ?: return getActionNoneNotification(data, action)

        return ActionCodeNotification(
            data.getString(KEY_TITLE) ?: "",
            data.getString(KEY_BODY) ?: "",
            data.getString(KEY_COLOR) ?: "",
            data.getString(KEY_ICON) ?: "",
            data.getString(KEY_PARAM) ?: "",
            context.getString(KEY_ID) ?: "",
            action.getString(KEY_VALUE) ?: ""
        )
    }
}