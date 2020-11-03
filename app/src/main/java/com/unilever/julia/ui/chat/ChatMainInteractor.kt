package com.unilever.julia.ui.chat

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.model.*
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.autenticacao.AutenticacaoInteractor
import io.reactivex.Observable

interface ChatMainInteractor : AutenticacaoInteractor {

    fun sendAdicionarAgenda(sessionCode: String, model: AdicionarAgendaModel.Item): Observable<Conversations>

    fun getInitData(): InitDataChatMain

    //fun sendInitialConversation(): Observable<Conversations>

    fun getToken(): Observable<Token>

    fun sendFeedback(sessionCode: String, code: String, text: String): Observable<Conversations>

    fun sendSugestao(sessionCode: String, code: String, text: String): Observable<Conversations>

    fun onCloseFlowInComponent(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations>

    fun sendDisambiguation(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations>

    fun getCustomers(): Observable<MutableList<Customer>>

    fun getTerritories(): Observable<MutableList<Territory>>

    fun sendCustomer(sessionCode: String, intent: String, customerId: String): Observable<Conversations>

    fun sendTerritory(sessionCode: String, intent: String, territory: String): Observable<Conversations>

    fun sendLogout(sessionCode: String): Observable<Boolean>
    fun getNewPushMessagesNotReads(notification: FirebaseNotification): Observable<Int>
    fun getNewPushMessagesNotReads(): Observable<Int>
}