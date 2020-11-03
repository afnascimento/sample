package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class SalesBulletinData(
    @SerializedName("salesBulletin")
    var salesBulletin: SalesBulletin? = SalesBulletin()
)