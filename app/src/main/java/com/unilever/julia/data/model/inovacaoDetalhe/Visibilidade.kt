package com.unilever.julia.data.model.inovacaoDetalhe

import com.google.gson.annotations.SerializedName

data class Visibilidade(
    @SerializedName("title")
    var name: String? = "",
    @SerializedName("instrucoes")
    var instrucoes: TitleAndDescription? = null,
    @SerializedName("material")
    var material: Material? = null
) {

    data class Material(
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("produtos")
        var produtos: MutableList<Produto>? = null
    ) {
        fun isValid(): Boolean {
            return !produtos.isNullOrEmpty()
        }
    }

    fun isValid(): Boolean {
        if (instrucoes?.isValid() == true)
            return true

        if (material?.isValid() == true)
            return true

        return false
    }
}