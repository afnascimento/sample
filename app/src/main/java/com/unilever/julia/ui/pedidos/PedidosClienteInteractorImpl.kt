package com.unilever.julia.ui.pedidos

import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.data.model.PedidosClienteModel
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import javax.inject.Inject

class PedidosClienteInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), PedidosClienteInteractor {

    override fun getPedidosCliente(model: PedidosClienteModel): Observable<MutableList<PedidoClienteResponse>> {
        return dataManager().juliaApi().getPedidosCliente(model)
            .flatMap { values ->
                if (values.isEmpty())
                    throw EmptyDataException()

                return@flatMap Observable.just(values)
            }
    }
}