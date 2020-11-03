package com.unilever.julia.ui.menu.notificacao

import com.google.gson.Gson
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.model.notificacao.Notifications
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import javax.inject.Inject

class NotificacaoInteractorImpl
@Inject constructor(dataManager: DataManager) : BaseInteractorImpl(dataManager), NotificacaoInteractor {

    private val mGson = Gson()

    override fun getNotifications(sessionCode: String): Observable<Notifications> {
        return sendIntent(sessionCode, JuliaIntent.Intent.NOTIFICACOES_00)
            .flatMap { conversations ->
                val model = mGson.fromJson(
                    conversations.getAnswer().technicalText,
                    Notifications::class.java
                )

                if (model.notifications.isNullOrEmpty()) {
                    savePushMessageNotReads(emptySet())
                    throw EmptyDataException()
                }

                return@flatMap Observable.just(model)
            }
    }

    override fun savePushMessageNotReads(idsNotReads : Set<String>) {
        dataManager().preferences().savePushMessageNotReads(idsNotReads)
    }
}