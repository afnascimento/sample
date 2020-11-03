package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName
import com.unilever.julia.utils.Utils

data class IpvFaseamento(
    @SerializedName("colorCode")
    var colorCode: String? = "",
    @SerializedName("entry")
    var entry: Double? = 0.0,
    @SerializedName("label")
    var label: String? = "",
    @SerializedName("limitDay")
    var limitDay: String? = "",
    @SerializedName("score")
    var score: Double? = 0.0,
    @SerializedName("status")
    var status: String? = "",
    @SerializedName("target")
    var target: Double? = 0.0,
    @SerializedName("message")
    var message: Message? = Message()
) {

    data class Message(
        @SerializedName("description")
        var description: String? = "",
        @SerializedName("icon")
        var icon: String? = "",
        @SerializedName("title")
        var title: String? = ""
    )

    enum class Type {
        active, expired, inactive
    }

    fun getTargetText(): String {
        return target?.toInt().toString()
    }

    fun getType(): Type {
        val find : String = status ?: "inactive"
        return Type.valueOf(find)
    }

    fun getLimitDayText(): String {
        val toDate = Utils.toDate(limitDay ?: "", "yyyy-MM-dd") ?: return ""
        return Utils.toStringByDate(toDate, "dd/MM")
    }
}