package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel
import org.apache.commons.lang3.StringUtils

class StatusPedidoCarteiraModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.STATUS_PEDIDO_CARTEIRA
    }

    var text : String = ""

    var context : Context? = null

    class Context {

        @Expose
        @SerializedName("intencao")
        var intencao: String? = null

        @Expose
        @SerializedName("territory")
        var territory: String? = null

        @Expose
        @SerializedName("qtyCustomer")
        var qtyCustomer: Int? = null

        @Expose
        @SerializedName("qtyOrders")
        var qtyOrders: Int? = null

        @Expose
        @SerializedName("valueOrders")
        var valueOrders: ValuePercent? = null

        @Expose
        @SerializedName("boxOrders")
        var boxOrders: ValuePercent? = null

        fun getTextTerritorio(): String {
            val temp = StringUtils.trimToEmpty(territory)
            if (StringUtils.isNotEmpty(temp)) {
                val split = StringUtils.split(temp, ";")
                if (split.size == 1) {
                    return split[0]
                } else if (split.size > 1) {
                    return "${split[0]} +${split.size - 1}..."
                }
            }
            return ""
        }
    }

    class ValuePercent {

        @Expose
        @SerializedName("total")
        var total: Double? = null

        @Expose
        @SerializedName("fact")
        var fact: ValueItem? = null

        @Expose
        @SerializedName("pend")
        var pend: ValueItem? = null

        @Expose
        @SerializedName("anul")
        var anul: ValueItem? = null
    }

    class ValueItem {

        @Expose
        @SerializedName("value")
        var value: Double? = null

        @Expose
        @SerializedName("color")
        var color: String? = null
    }
}