package com.unilever.julia.ui.clientes

import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.exception.EmptyDataException
import io.reactivex.Observable
import javax.inject.Inject

class ClientesInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), ClientesInteractor {

    override fun getClientes(sessionCode: String, intention: String, territory: String): Observable<MutableList<ClienteModel>> {
        return dataManager().juliaApi().getClientes(sessionCode, intention, territory)
            .flatMap { value ->
                if (value.isNullOrEmpty()) {
                    throw EmptyDataException()
                }
                return@flatMap Observable.just(value)
            }
    }
}