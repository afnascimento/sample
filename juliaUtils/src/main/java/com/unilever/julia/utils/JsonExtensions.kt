package com.unilever.julia.utils

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject

fun JSONObject.getAsObject(memberName: String): JSONObject? {
    if (has(memberName)) {
        return try {
            getJSONObject(memberName)
        } catch (e : Throwable) {
            null
        }
    }
    return null
}

fun JSONObject.getAsString(memberName: String): String? {
    if (has(memberName)) {
        return try {
            getString(memberName)
        } catch (e : Throwable) {
            null
        }
    }
    return null
}

fun JsonObject.getString(memberName: String): String? {
    if (has(memberName)) {
        return try {
            get(memberName).asString
        } catch (e : Throwable) {
            null
        }
    }
    return null
}

fun JsonObject.getBoolean(memberName: String): Boolean {
    if (has(memberName)) {
        return try {
            get(memberName).asBoolean
        } catch (e : Throwable) {
            false
        }
    }
    return false
}

fun JsonObject.getInt(memberName: String): Int? {
    if (has(memberName)) {
        return try {
            get(memberName).asInt
        } catch (e : Throwable) {
            null
        }
    }
    return null
}

fun JsonObject.getJsonObject(memberName: String): JsonObject? {
    if (has(memberName)) {
        return try {
            get(memberName).asJsonObject
        } catch (e : Throwable) {
            null
        }
    }
    return null
}

fun JsonObject.getJsonObjectByMemberString(memberName: String): JsonObject? {
    return try {
        val jsonString : String = getString(memberName) ?: return null
        val element = JsonParser().parse(jsonString)
        element.asJsonObject
    } catch (e : Throwable) {
        null
    }
}

fun JsonObject.getJsonArray(memberName: String): JsonArray? {
    if (has(memberName)) {
        return try {
            get(memberName).asJsonArray
        } catch (e : Throwable) {
            null
        }
    }
    return null
}