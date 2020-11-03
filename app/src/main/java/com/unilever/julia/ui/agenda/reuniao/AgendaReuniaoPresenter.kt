package com.unilever.julia.ui.agenda.reuniao

import com.unilever.julia.data.model.EmailParticipante
import com.unilever.julia.ui.base.BasePresenter

interface AgendaReuniaoPresenter<V : AgendaReuniaoView, I : AgendaReuniaoInteractor> : BasePresenter<V, I> {
    fun enviarGravacaoAgenda(
        idReuniao: String?,
        tituloReuniao: String,
        dtInicio: String,
        dtFim: String,
        hrInicio: String,
        hrFim: String,
        local: String,
        participantes: MutableList<EmailParticipante>?,
        sessionCode: String
    )

    fun onClickButtonCancelar(owner: Boolean)
    fun enviarExclusaoAgenda(idReuniao: String, sessionCode: String)
}