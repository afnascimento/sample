package com.unilever.julia.ui.status.pendentes

import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.base.BaseView

interface StatusPendentesView : BaseView {
    fun addJuliaStatusCardItems(items: MutableList<StatusPedidoModel.Item>)
}