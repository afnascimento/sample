package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvPositivation(
    @SerializedName("posivation")
    var clients: List<IpvClient>? = emptyList()
)