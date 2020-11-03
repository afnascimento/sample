package com.unilever.julia.ui.clientes

import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.ui.base.*

interface ClientesPresenter<V : ClientesView, I : ClientesInteractor> : BasePresenter<V, I> {

    fun getClientes(sessionCode: String, intention: String, territory: String)
    fun filterItems(text: String)
    fun filterItems(text: String, dataSet: MutableList<ClienteModel>): MutableList<ClienteModel>
    fun clearFilter()
    fun sortItems(asc: Boolean)
    fun sortItems(asc: Boolean, dataSet: MutableList<ClienteModel>): MutableList<ClienteModel>
    fun setTitleToolbar(territory: String)
}