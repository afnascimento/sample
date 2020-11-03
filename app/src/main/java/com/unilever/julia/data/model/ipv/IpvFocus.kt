package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvFocus(
    @SerializedName("focus")
    var clients: List<IpvClient>? = emptyList()
)