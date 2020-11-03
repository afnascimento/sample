package com.unilever.julia.ui.inovacao.detailV2.model

import android.graphics.drawable.Drawable
import com.unilever.julia.components.innovations.InnovationTradestory
import com.unilever.julia.components.innovations.InnovationsMidias
import com.unilever.julia.data.model.inovacaoDetalhe.*

interface InnovationFragmentData :
    InnovationTradestory.Listener,
    InnovationsMidias.Listener {

    fun getResumo(): Resumo

    fun getExecucao(): Execucao

    fun getVisibilidade(): Visibilidade

    fun getNovosSkus(): NovosSkus

    fun getSkusDeslistados(): SkusDeslistados

    fun onClickProductRedirectGallery(position : Int, products: List<Produto>)

    fun getDrawableBrand(): Drawable
}