package com.unilever.julia.ui.agenda.reuniao

import com.google.gson.Gson
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.*
import com.unilever.julia.exception.AgendaOutlookException
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class AgendaReuniaoInteractorImpl
@Inject
constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), AgendaReuniaoInteractor {

    private var mGson: Gson = Gson()

    override fun enviarGravacaoAgenda(sessionCode: String, send: AgendaOutlookGravacao): Observable<AgendaOutlookSucesso> {
        return dataManager().juliaApi().sendReuniaoOutlookGravacao(sessionCode, send)
            .flatMap { conversations ->
                // ERRO
                val answer = conversations.getAnswer()
                if (!StringUtils.containsIgnoreCase(answer.title, "OUTLOOK_SUCESSO")) {
                    throw AgendaOutlookException(answer.text)
                }

                val context = mGson.fromJson(StringUtils.trimToEmpty(answer.technicalText), AgendaOutlookContext::class.java)

                val agenda = AgendaOutlookSucesso(StringUtils.trimToEmpty(answer.text), context)

                return@flatMap Observable.just(agenda)
            }
    }
}