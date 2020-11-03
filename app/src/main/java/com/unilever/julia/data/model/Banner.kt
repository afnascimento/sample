package com.unilever.julia.data.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class Banner(
    @SerializedName("url")
    var url : String = "",
    @SerializedName("visible")
    var visible : Boolean = false
) {

    fun isValid(): Boolean {
        return url.isNotEmpty()
    }

    class Deserializer : JsonDeserializer<Banner> {

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Banner {
            val jsonObject : JsonObject = json?.asJsonObject ?: return Banner()
            if (jsonObject.has("banner")) {
                val bannerJsonObject = jsonObject.getAsJsonObject("banner")

                var url = ""
                if (bannerJsonObject.has("url")) {
                    url = bannerJsonObject.get("url").asString
                }

                var visible = false
                if (bannerJsonObject.has("visible")) {
                    visible = bannerJsonObject.get("visible").asBoolean
                }

                return Banner(url, visible)
            }
            return Banner()
        }
    }

}