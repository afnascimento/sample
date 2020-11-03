package com.unilever.julia.ui.inovacao.detalhe

import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.ui.base.BasePresenter

interface InovacaoDetalhePresenter<V : InovacaoDetalheView, I : InovacaoDetalheInteractor> : BasePresenter<V, I> {
    fun sendEventInnovation(model: InovacaoProjetoModel)
    fun sendEventTradestory(model: InovacaoProjetoModel)
}