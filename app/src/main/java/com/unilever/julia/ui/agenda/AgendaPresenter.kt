package com.unilever.julia.ui.agenda

import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.AgendaModel
import com.unilever.julia.ui.base.BasePresenter

interface AgendaPresenter<V : AgendaView, I : AgendaInteractor> : BasePresenter<V, I> {
    fun agendaMudarStatusEvento(sessionCode: String, status: AgendaStatusEnum, item: AgendaModel.Item)
}