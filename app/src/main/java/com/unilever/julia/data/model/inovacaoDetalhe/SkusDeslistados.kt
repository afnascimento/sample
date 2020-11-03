package com.unilever.julia.data.model.inovacaoDetalhe


import com.google.gson.annotations.SerializedName

data class SkusDeslistados(
    @SerializedName("title")
    var name: String? = "",
    @SerializedName("produtos")
    var produtos: MutableList<Produto>? = null
) {
    fun isValid(): Boolean {
        return !produtos.isNullOrEmpty()
    }
}