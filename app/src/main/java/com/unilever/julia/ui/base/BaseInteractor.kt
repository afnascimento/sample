package com.unilever.julia.ui.base

import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.firebase.FirebaseAnalyticsApp
import io.reactivex.Observable

/**
 * Created by andre.nascimento on 27/12/2018.
 */
interface BaseInteractor {

    fun sendIntent(sessionCode: String, intent: JuliaIntent.Intent): Observable<Conversations>

    fun sendConversations(sessionCode : String, text: String): Observable<Conversations>

    fun sendStatusPedido(parcel: SendStatusParcel): Observable<Conversations>

    fun isTextToSpeech(): Observable<Boolean>

    fun isOutlook(): Observable<Boolean>

    fun isPedidos(): Observable<Boolean>

    fun saveTextToSpeech(enabled: Boolean): Observable<Boolean>

    fun saveOutlook(enabled: Boolean): Observable<Boolean>

    fun saveIPV(enabled: Boolean): Observable<Boolean>

    fun getUser(): Observable<User>

    fun sendAgendaStatusChange(
        sessionCode: String,
        status: AgendaStatusEnum,
        item: AgendaModel.Item
    ): Observable<Conversations>

    fun getCountEvent(event: FirebaseAnalyticsApp.Events): Observable<Int>

    fun firebaseAnalytics(): FirebaseAnalyticsApp

    fun enviarExclusaoAgenda(sessionCode: String, send: AgendaOutlookExcluir): Observable<String>

    fun getUserLogged(): User

    fun saveUserPreferences(user: User): Observable<User>
}