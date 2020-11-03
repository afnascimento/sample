package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class SendListaInovacoes {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("marca_id")
        var marcaId = ""

        @SerializedName("categoria_id")
        var categoriaId = ""

        @SerializedName("commodity_id")
        var commodityId = ""

        constructor()

        constructor(marcaId: String, categoriaId: String, commodityId: String) {
            this.marcaId = marcaId
            this.categoriaId = categoriaId
            this.commodityId = commodityId
        }
    }
}
