package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class UpdateInformations(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("updated")
    var updated: String? = "",
    @SerializedName("value")
    var value: Double? = 0.0,
    @SerializedName("color")
    var color: String? = ""
)