package com.unilever.julia.ui.component

import com.unilever.julia.data.model.AgendaModel

interface IJuliaAgendaSwipeCardListener {

    fun onDetalheEvento(item : AgendaModel.Item)
    fun onCancelarEvento(item : AgendaModel.Item)
    fun onConcluirEvento(item : AgendaModel.Item)
    fun onExcluirEvento(item : AgendaModel.Item)
}