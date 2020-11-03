package com.unilever.julia.data.model.ipv.manager

import com.google.gson.annotations.SerializedName
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvModel

data class IpvMember(
    @SerializedName("code")
    var code: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("role")
    var role: String? = "",
    @SerializedName("ipv")
    var ipv: IpvModel? = null,
    @SerializedName("context")
    var context: IpvContext? = null
)