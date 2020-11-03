package com.unilever.julia.ui.base

import com.unilever.julia.data.DataManager
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.api.JuliaUnileverApi
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.exception.AgendaOutlookException
import com.unilever.julia.firebase.FirebaseAnalyticsApp
import io.reactivex.Observable
import org.apache.commons.lang3.StringUtils
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by andre.nascimento on 27/12/2018.
 */
open class BaseInteractorImpl
@Inject constructor(private val mDataManager: DataManager) : BaseInteractor {

    override fun saveUserPreferences(user: User): Observable<User> {
        return Observable.fromCallable {
            dataManager().preferences().saveUser(user)
            return@fromCallable user
        }
    }

    override fun firebaseAnalytics(): FirebaseAnalyticsApp {
        return mDataManager.firebaseAnalytics()
    }

    override fun getCountEvent(event: FirebaseAnalyticsApp.Events): Observable<Int> {
        return Observable.just(mDataManager.preferences().getEventCount(event))
    }

    override fun getUserLogged(): User  {
        return mDataManager.preferences().getUser() ?: User()
    }

    override fun getUser(): Observable<User> {
        return Observable.just(mDataManager.preferences().getUser())
    }

    fun dataManager(): DataManager {
        return mDataManager
    }

    fun juliaUnileverApi(): JuliaUnileverApi {
        return mDataManager.juliaApi()
    }

    override fun sendIntent(sessionCode : String, intent: JuliaIntent.Intent): Observable<Conversations> {
        return juliaUnileverApi().sendIntent(sessionCode, intent)
    }

    override fun sendConversations(sessionCode : String, text: String): Observable<Conversations> {
        return juliaUnileverApi().sendConversations(sessionCode, text)
    }

    override fun sendStatusPedido(parcel: SendStatusParcel): Observable<Conversations> {
        return juliaUnileverApi().sendStatusPedido(parcel)
    }

    override fun isTextToSpeech(): Observable<Boolean> {
        return Observable.just(mDataManager.preferences().isTextToSpeech())
    }

    override fun saveTextToSpeech(enabled: Boolean): Observable<Boolean> {
        return Observable.fromCallable {
            mDataManager.preferences().saveTextToSpeech(enabled)
            return@fromCallable true
        }
    }

    override fun isOutlook(): Observable<Boolean> {
        return Observable.just(mDataManager.preferences().isOutlook())
    }

    override fun saveOutlook(enabled: Boolean): Observable<Boolean> {
        return Observable.fromCallable {
            mDataManager.preferences().saveOutlook(enabled)
            return@fromCallable true
        }
    }

    override fun isPedidos(): Observable<Boolean> {
        return Observable.just(mDataManager.preferences().isIPV())
    }

    override fun saveIPV(enabled: Boolean): Observable<Boolean> {
        return Observable.fromCallable {
            mDataManager.preferences().saveIPV(enabled)
            return@fromCallable true
        }
    }

    override fun sendAgendaStatusChange(sessionCode: String, status: AgendaStatusEnum, item: AgendaModel.Item): Observable<Conversations> {
        return mDataManager.juliaApi().sendAgendaStatusChange(sessionCode, status, item).delay(1, TimeUnit.SECONDS)
    }

    override fun enviarExclusaoAgenda(sessionCode: String, send: AgendaOutlookExcluir): Observable<String> {
        return dataManager().juliaApi().sendReuniaoOutlookExcluir(sessionCode, send)
            .flatMap { conversations ->
                // ERRO
                val answer = conversations.getAnswer()
                if (!StringUtils.containsIgnoreCase(answer.title, "AGENDA_OUTLOOK_EXCLUIR_SUCESSO")) {
                    throw AgendaOutlookException(answer.text)
                }

                return@flatMap Observable.just(StringUtils.trimToEmpty(answer.text))
            }
    }
}