package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class AttendanceResponse(
    @SerializedName("territory")
    var territory: Map<String, String>? = mapOf(),
    @SerializedName("district")
    var district: Map<String, String>? = mapOf(),
    @SerializedName("hitRegional")
    var hitRegional: List<String>? = listOf(),
    @SerializedName("subsidiary")
    var subsidiary: List<String>? = listOf()
)