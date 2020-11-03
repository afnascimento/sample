package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvPriorities(
    @SerializedName("priorities")
    var clients: List<IpvClient>? = emptyList()
)