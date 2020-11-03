package com.unilever.julia.ui.status.detalhe

import com.unilever.julia.data.model.IStatusPedido
import com.unilever.julia.ui.base.BaseView

interface StatusDetalheView : BaseView {
    fun setItems(items: MutableList<IStatusPedido>)
    fun clearTextEditText()
}