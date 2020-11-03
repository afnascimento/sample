package com.unilever.julia.ui.agenda.reuniao

import com.unilever.julia.data.model.AgendaOutlookGravacao
import com.unilever.julia.data.model.AgendaOutlookSucesso
import com.unilever.julia.ui.base.BaseView

interface AgendaReuniaoView : BaseView {
    fun agendaGravadaSucesso(agenda: AgendaOutlookSucesso)
    fun setErrorTituloReuniao(error: String?)
    fun setErrorLocal(error: String?)
    fun setErrorHorarioInicio(error: String?)
    fun setErrorHorarioFim(error: String?)
    fun setErrorDataInicio(error: String?)
    fun setErrorDataFim(error: String?)
    fun setErrorParticipante(index: Int, textError: String?)
    fun onCancelar()
    fun onExcluirDialog()
    fun agendaExcluidaSucesso(text: String)
}