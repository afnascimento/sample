package com.unilever.julia.data.model.ipv.manager

import com.google.gson.annotations.SerializedName

data class IpvManagement(
    @SerializedName("summary")
    var summary: IpvSummary? = IpvSummary()
)