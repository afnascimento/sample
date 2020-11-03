package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class StatusPedidoResponse {

    @SerializedName("codProduto")
    @Expose
    var codProduto: String? = null

    @SerializedName("valorProduto")
    @Expose
    var valorProduto: String? = null

    @SerializedName("quantidade")
    @Expose
    var quantidade: String? = null

    @SerializedName("motivo")
    @Expose
    var motivo: String? = null

    @SerializedName("dataCriacao")
    @Expose
    var dataCriacao: String? = null

    @SerializedName("dataRequerida")
    @Expose
    var dataRequerida: String? = null

    @SerializedName("dataAgenda")
    @Expose
    var dataAgenda: String? = null

    @SerializedName("codPedido")
    @Expose
    var codPedido: String? = null

    @SerializedName("statusPedido")
    @Expose
    var statusPedido: String? = null

    @SerializedName("customerName")
    @Expose
    var customerName: String? = null

    @SerializedName("materialName")
    @Expose
    var materialName: String? = null

    @SerializedName("orderTranspFat")
    @Expose
    var items: MutableList<Item>? = null

    class Item {

        @SerializedName("codigo")
        @Expose
        var codigo : String? = null

        @SerializedName("data")
        @Expose
        var data : String? = null

        @SerializedName("quantidade")
        @Expose
        var quantidade : String? = null
    }
}
