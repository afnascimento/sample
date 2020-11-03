package com.unilever.julia.ui.menu.notificacao

import com.google.gson.Gson
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.notificacao.Notifications
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.firebase.database.DatabaseRealtime
import com.unilever.julia.firebase.exception.NotFoundDataException
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificacaoInteractorFirebase
@Inject constructor(dataManager: DataManager) : BaseInteractorImpl(dataManager), NotificacaoInteractor {

    override fun savePushMessageNotReads(idsNotReads: Set<String>) {
    }

    private val mGson = Gson()

    override fun getNotifications(sessionCode: String): Observable<Notifications> {
        return DatabaseRealtime.getObservableValue(mGson, "tela_notificacoes", Notifications::class.java)
            .delay(1, TimeUnit.SECONDS)
            .onErrorResumeNext(object :
                io.reactivex.functions.Function<Throwable, Observable<Notifications>> {
                override fun apply(error: Throwable): Observable<Notifications> {
                    if (error is NotFoundDataException) {
                        return Observable.error(EmptyDataException())
                    }
                    return Observable.just(Notifications())
                }
            })
            .flatMap { model ->
                if (model.notifications.isNullOrEmpty()) {
                    throw EmptyDataException()
                }
                return@flatMap Observable.just(model)
            }
    }
}