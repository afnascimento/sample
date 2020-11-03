package com.unilever.julia.data.model.ipv.manager

import com.google.gson.annotations.SerializedName
import com.unilever.julia.data.model.ipv.IpvModel

data class IpvSummary(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("role")
    var role: String? = "",
    @SerializedName("ipv")
    var ipv: IpvModel? = null,
    @SerializedName("members")
    var members: List<IpvMember>? = emptyList()
)