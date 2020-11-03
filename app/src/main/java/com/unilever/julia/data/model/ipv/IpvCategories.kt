package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

data class IpvCategories(
    @SerializedName("categories")
    var categories: List<IpvCategory>? = listOf()
)