package com.unilever.julia.ui.pedidos

import com.unilever.julia.data.model.FiltroModel
import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.data.model.PedidosClienteModel
import com.unilever.julia.ui.base.BasePresenter

interface PedidosClientePresenter<V : PedidosClienteView, I : PedidosClienteInteractor> : BasePresenter<V, I> {
    fun getPedidosCliente(model: PedidosClienteModel)
    fun redirectFiltroPedido()
    fun filtrarDados(filtro: FiltroModel)
    fun filtrarDados(filtro: FiltroModel, dataSet: MutableList<PedidoClienteResponse>)
}