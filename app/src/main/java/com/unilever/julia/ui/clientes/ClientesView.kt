package com.unilever.julia.ui.clientes

import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.ui.base.*

interface ClientesView : BaseView {

    fun addItems(items: MutableList<ClienteModel>)
    fun showRecyclerViewItems()
    fun hideRecyclerViewItems()
    fun showContentLoadView()
    fun hideContentLoadView()
    fun setTextFilterInput(text: String)
    fun setTitle(title: String)
}