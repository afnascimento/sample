package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvOffersProducts(
    @SerializedName("products")
    var products: List<IpvOfferProduct>? = listOf()
)