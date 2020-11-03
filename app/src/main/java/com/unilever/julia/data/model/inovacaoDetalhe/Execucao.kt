package com.unilever.julia.data.model.inovacaoDetalhe

import com.google.gson.annotations.SerializedName

data class Execucao(
    @SerializedName("title")
    var name: String? = "",
    @SerializedName("regrasDeOuro")
    var regrasDeOuro: TitleAndDescription? = null,
    @SerializedName("planograma")
    var planograma: Planograma? = null
) {
    data class Planograma(
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
        if (regrasDeOuro?.isValid() == true)
            return true

        if (planograma?.isValid() == true)
            return true

        return false
    }

    /*
    data class RegrasDeOuro(
        @SerializedName("title")
        var title: String? = "",
        @SerializedName("description")
        var description: String? = ""
    )
     */
}