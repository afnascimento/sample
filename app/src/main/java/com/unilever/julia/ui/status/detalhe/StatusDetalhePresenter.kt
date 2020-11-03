package com.unilever.julia.ui.status.detalhe

import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.base.BasePresenter

interface StatusDetalhePresenter<V : StatusDetalheView, I : StatusDetalheInteractor> : BasePresenter<V, I> {
    fun sendStatusPedido(sessionCode: String, model: StatusPedidoModel.Item)
    fun filterItems(text: String)
}