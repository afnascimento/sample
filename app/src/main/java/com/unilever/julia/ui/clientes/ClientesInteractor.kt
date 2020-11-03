package com.unilever.julia.ui.clientes

import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.ui.base.*
import io.reactivex.Observable

interface ClientesInteractor : BaseInteractor {

    fun getClientes(sessionCode: String, intention: String, territory: String): Observable<MutableList<ClienteModel>>
}