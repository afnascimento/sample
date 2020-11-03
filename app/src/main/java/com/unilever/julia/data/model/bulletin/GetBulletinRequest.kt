package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class GetBulletinRequest(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("context")
    var context: Context = Context()
) {

    fun addAttendance(items: List<String>) {
        if (items.isNotEmpty())
            context.salesBulletinsFilters.attendance.addAll(items)
    }

    fun addBrand(items: List<String>) {
        if (items.isNotEmpty())
            context.salesBulletinsFilters.brand.addAll(items)
    }

    fun addCategory(items: List<String>) {
        if (items.isNotEmpty())
            context.salesBulletinsFilters.category.addAll(items)
    }

    fun addCommodity(items: List<String>) {
        if (items.isNotEmpty())
            context.salesBulletinsFilters.commodity.addAll(items)
    }

    fun addCustomer(items: List<String>) {
        if (items.isNotEmpty())
            context.salesBulletinsFilters.customer.addAll(items)
    }

    fun addChannel(items: List<String>) {
        if (items.isNotEmpty())
            context.salesBulletinsFilters.helpChannel.addAll(items)
    }

    fun setTerritory(territory: String) {
        context.territory = territory
    }

    data class Context(
        @SerializedName("salesBulletinsFilters")
        var salesBulletinsFilters: SalesBulletinsFilters = SalesBulletinsFilters(),
        @SerializedName("territory")
        var territory: String = ""
    )

    data class SalesBulletinsFilters(
        @SerializedName("attendance")
        var attendance: MutableList<String> = arrayListOf(),
        @SerializedName("brand")
        var brand: MutableList<String> = arrayListOf(),
        @SerializedName("category")
        var category: MutableList<String> = arrayListOf(),
        @SerializedName("commodity")
        var commodity: MutableList<String> = arrayListOf(),
        @SerializedName("customer")
        var customer: MutableList<String> = arrayListOf(),
        @SerializedName("helpChannel")
        var helpChannel: MutableList<String> = arrayListOf()
    )
}