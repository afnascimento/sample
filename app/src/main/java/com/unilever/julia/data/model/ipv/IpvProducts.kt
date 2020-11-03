package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvProducts(
    @SerializedName("products")
    var products: List<IpvProduct>? = listOf()
)