package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class BulletinsMultipleFiltersRequest(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("context")
    var context: Context = Context()
) {

    fun addDistrict(item: String) {
        if (item.isNotEmpty())
            context.district.add(item)
    }

    fun addRegional(item: String) {
        if (item.isNotEmpty())
            context.hitRegional.add(item)
    }

    fun addFilial(item: String) {
        if (item.isNotEmpty())
            context.subsidiary.add(item)
    }

    fun addTerritory(item: String) {
        if (item.isNotEmpty())
            context.territory.add(item)
    }

    data class Context(
        @SerializedName("district")
        var district: MutableList<String> = arrayListOf(),
        @SerializedName("hitRegional")
        var hitRegional: MutableList<String> = arrayListOf(),
        @SerializedName("subsidiary")
        var subsidiary: MutableList<String> = arrayListOf(),
        @SerializedName("territory")
        var territory: MutableList<String> = arrayListOf()
    )
}