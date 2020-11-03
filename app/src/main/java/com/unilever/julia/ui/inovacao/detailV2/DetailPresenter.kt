package com.unilever.julia.ui.inovacao.detailV2

import android.content.Context
import android.graphics.drawable.Drawable
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.data.model.inovacaoDetalhe.*
import com.unilever.julia.ui.base.*

interface DetailPresenter<V : DetailView, I : DetailInteractor> : BasePresenter<V, I> {

    fun getDetail(context: Context, model: InnovationProjectsModel.Item)
    fun putDownloadTradestory(model: InnovationProjectsModel.Item, urlDownload: String)
    fun getResumo(): Resumo
    fun getExecucao(): Execucao
    fun getVisibilidade(): Visibilidade
    fun getNovosSkus(): NovosSkus
    fun getSkusDeslistados(): SkusDeslistados
    fun getDrawableBrand(): Drawable
    fun updateTradestorySuccess(tradestory: Resumo.TradeStory)
}