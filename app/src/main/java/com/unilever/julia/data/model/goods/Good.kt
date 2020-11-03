package com.unilever.julia.data.model.goods

import com.google.gson.annotations.SerializedName

data class Good(
    @SerializedName("groups")
    var groups: List<IpvGoodsGroup>? = listOf(),
    @SerializedName("unitary")
    var unitary: List<IpvGoodsUnitary>? = listOf()
)