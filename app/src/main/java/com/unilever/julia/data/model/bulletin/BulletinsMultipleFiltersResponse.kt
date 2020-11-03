package com.unilever.julia.data.model.bulletin

import com.google.gson.annotations.SerializedName

data class BulletinsMultipleFiltersResponse(
    @SerializedName("territory")
    var territory: String? = "",
    @SerializedName("salesBulletinsFilters")
    var salesBulletinsFilters: SalesBulletinsFilters? = SalesBulletinsFilters()
) {

    enum class Type {
        CANAIS, CLIENTES, CATEGORIAS, MARCAS, COMMODITIES
    }

    data class SalesBulletinsFilters(
        @SerializedName("brand")
        var brand: List<String>? = emptyList(),
        @SerializedName("category")
        var category: List<String>? = emptyList(),
        @SerializedName("commodity")
        var commodity: List<String>? = emptyList(),
        @SerializedName("customer")
        var customer: List<String>? = emptyList(),
        @SerializedName("helpChannel")
        var helpChannel: List<String>? = emptyList()
    )
}