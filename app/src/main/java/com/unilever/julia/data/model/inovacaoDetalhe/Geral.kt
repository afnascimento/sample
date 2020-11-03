package com.unilever.julia.data.model.inovacaoDetalhe


import com.google.gson.annotations.SerializedName

data class Geral(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("icon")
    var icon: String? = "",
    @SerializedName("image")
    var image: String? = ""
)