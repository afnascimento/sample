package com.unilever.julia.ui.agenda.reuniao

import com.unilever.julia.data.model.AgendaOutlookGravacao
import com.unilever.julia.data.model.AgendaOutlookSucesso
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface AgendaReuniaoInteractor : BaseInteractor {
    fun enviarGravacaoAgenda(sessionCode: String, send: AgendaOutlookGravacao): Observable<AgendaOutlookSucesso>
}