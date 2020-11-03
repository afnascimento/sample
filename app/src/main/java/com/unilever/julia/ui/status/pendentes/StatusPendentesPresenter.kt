package com.unilever.julia.ui.status.pendentes

import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.base.BasePresenter

interface StatusPendentesPresenter<V : StatusPendentesView, I : StatusPendentesInteractor> : BasePresenter<V, I> {
    fun getPendentes(sessionCode: String, model: StatusPedidoModel.Item)
}