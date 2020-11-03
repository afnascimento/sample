package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class ResultInformation(
    @SerializedName("percentage")
    var percentage: Double? = 0.0,
    @SerializedName("result_description")
    var resultDescription: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("value")
    var value: Double? = 0.0,
    @SerializedName("color")
    var color: String? = null
)