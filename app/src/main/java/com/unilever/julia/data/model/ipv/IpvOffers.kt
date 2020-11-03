package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvOffers(
    @SerializedName("coordinator")
    var coordinator: String? = "",
    @SerializedName("districtColorCode")
    var districtColorCode: String? = "",
    @SerializedName("districtName")
    var districtName: String? = "",
    @SerializedName("districtScore")
    var districtScore: Double? = 0.0,
    @SerializedName("offers")
    var offers: List<IpvOffer>? = listOf(),
    @SerializedName("vendorColorCode")
    var vendorColorCode: String? = "",
    @SerializedName("vendorName")
    var vendorName: String? = "",
    @SerializedName("vendorScore")
    var vendorScore: Double? = 0.0
)