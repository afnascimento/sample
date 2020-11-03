package com.unilever.julia.data.model.goods

import com.google.gson.annotations.SerializedName

data class Goods(
    @SerializedName("goods")
    var goods: List<Good>? = listOf()
)