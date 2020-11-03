package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvInnovation(
    @SerializedName("innovation")
    var clients: List<IpvClient>? = emptyList()
)