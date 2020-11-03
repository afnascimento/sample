package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class IpvCardChat : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.IPV
    }

    var text : String = ""

    var nameIpv : String = ""

    var visibleCard = true

    @SerializedName("ipv")
    var ipv : IpvModel? = null

    @SerializedName("context")
    var context : IpvContext? = null
}