package com.unilever.julia.ui.component

import com.unilever.julia.data.model.AgendaModel

interface IJuliaAgendaSwipeCard {

    fun addItem(item : AgendaModel.Item)
    fun setVisibleData(visible: Boolean)
    fun setListener(listener: IJuliaAgendaSwipeCardListener)
}