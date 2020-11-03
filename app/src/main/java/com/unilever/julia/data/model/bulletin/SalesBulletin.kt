package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class SalesBulletin(
    @SerializedName("territory")
    var territory: String? = "",
    @SerializedName("sales")
    var sales: List<Sale>? = listOf()
)