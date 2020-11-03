package com.unilever.julia.ui.pedidos

import com.unilever.julia.data.model.FiltroModel
import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.ui.base.BaseView

interface PedidosClienteView : BaseView {
    fun addItems(items: MutableList<PedidoClienteResponse>)
    fun redirectFiltroPedidoActivity(filtroModel: FiltroModel)
}