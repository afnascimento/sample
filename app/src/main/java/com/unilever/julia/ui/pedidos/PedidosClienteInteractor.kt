package com.unilever.julia.ui.pedidos

import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.data.model.PedidosClienteModel
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface PedidosClienteInteractor : BaseInteractor {
    fun getPedidosCliente(model: PedidosClienteModel): Observable<MutableList<PedidoClienteResponse>>
}