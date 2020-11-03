package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class SendStatusPedido {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("customer_id")
        var customerId = ""

        @SerializedName("order_id")
        var orderId = ""

        @SerializedName("status_pedido")
        var statusPedido = ""

        @SerializedName("id_region")
        var idRegion = ""

        constructor()

        constructor(customerId: String, orderId: String, statusPedido: String, idRegion: String) {
            this.customerId = customerId
            this.orderId = orderId
            this.statusPedido = statusPedido
            this.idRegion = idRegion
        }
    }
}
