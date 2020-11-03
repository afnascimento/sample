package com.unilever.julia.data.model.inovacaoDetalhe

import com.google.gson.annotations.SerializedName

data class InovacaoDetalhe(
    @SerializedName("geral")
    var geral: Geral? = null,
    @SerializedName("resumo")
    var resumo: Resumo? = null,
    @SerializedName("execucao")
    var execucao: Execucao? = null,
    @SerializedName("novosSkus")
    var novosSkus: NovosSkus? = null,
    @SerializedName("skusDeslistados")
    var skusDeslistados: SkusDeslistados? = null,
    @SerializedName("visibilidade")
    var visibilidade: Visibilidade? = null
)