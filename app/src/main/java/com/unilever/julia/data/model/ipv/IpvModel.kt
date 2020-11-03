package com.unilever.julia.data.model.ipv

import com.google.gson.annotations.SerializedName

class IpvModel {

    @SerializedName("target")
    var target : IpvItem? = null

    @SerializedName("score")
    var score : IpvItem? = null

    @SerializedName("items")
    var items : List<IpvItem>? = null

    private fun selectorGetValue(p: IpvItem): Double = p.percentage ?: 0.0

    fun getItemsSorted(): MutableList<IpvItem> {
        val sortedBy = items?.sortedBy { selectorGetValue(it) }
        return sortedBy?.toMutableList() ?: arrayListOf()
    }
}