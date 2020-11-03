package com.unilever.julia.ui.chat

import com.unilever.julia.components.model.ButtonClickListenerModel
import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.model.*
import com.unilever.julia.firebase.FirebaseAnalyticsApp
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.autenticacao.AutenticacaoInteractorImpl
import io.reactivex.Observable

class ChatMainInteractorImpl
@Inject constructor(mDataManager: DataManager) : AutenticacaoInteractorImpl(mDataManager), ChatMainInteractor {

    override fun getCustomers() : Observable<MutableList<Customer>> {
        return dataManager().database().customerDao().getAllCustomers()
    }

    override fun getTerritories(): Observable<MutableList<Territory>> {
        return dataManager().database().territoryDao().getAllTerritories()
    }

    /*
    override fun sendInitialConversation(): Observable<Conversations> {
        return juliaUnileverApi().sendConversations(TEXT_INITIAL)
        return Observable.zip(juliaUnileverApi().sendConversations(TEXT_INITIAL), getUser(),
            BiFunction<Conversations, User, Pair<Conversations, User>> { conversations, user -> Pair(conversations, user) })
    }
    */

    override fun getNewPushMessagesNotReads(notification: FirebaseNotification): Observable<Int> {
        return Observable.just(
            dataManager().preferences().getPushMessagesNotReads(notification)
        )
    }

    override fun getNewPushMessagesNotReads(): Observable<Int> {
        return Observable.just(
            dataManager().preferences().getPushMessagesNotReads()
        )
    }

    override fun getInitData(): InitDataChatMain {
        return InitDataChatMain(
            dataManager().preferences().isTextToSpeech(),
            getUserLogged(),
            dataManager().preferences().getEventCount(FirebaseAnalyticsApp.Events.VOICE_SETTING_ENABLED),
            dataManager().preferences().getBanner()?.url ?: "",
            dataManager().preferences().getBanner()?.visible ?: false
        )
    }

    override fun sendAdicionarAgenda(sessionCode : String, model: AdicionarAgendaModel.Item): Observable<Conversations> {
        return juliaUnileverApi().sendAdicionarAgenda(sessionCode, model)
    }

    override fun getToken() : Observable<Token> {
        val token : Token = dataManager().preferences().getToken() ?: Token()
        return Observable.just(token)
    }

    override fun sendFeedback(sessionCode: String, code: String, text: String): Observable<Conversations> {
        return dataManager().juliaApi().sendFeedback(sessionCode, code, text)
    }

    override fun sendSugestao(sessionCode: String, code: String, text: String): Observable<Conversations> {
        return dataManager().juliaApi().sendSugestao(sessionCode, code, text)
    }

    override fun onCloseFlowInComponent(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations> {
        return dataManager().juliaApi().sendCloseFlowInComponent(sessionCode, model)
    }

    override fun sendDisambiguation(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations> {
        return dataManager().juliaApi().sendDisambiguation(sessionCode, model)
    }

    override fun sendCustomer(sessionCode: String, intent: String, customerId: String): Observable<Conversations> {
        return dataManager().juliaApi().sendCustomer(sessionCode, intent, customerId)
    }

    override fun sendTerritory(sessionCode: String, intent: String, territory: String): Observable<Conversations> {
        return dataManager().juliaApi().sendTerritory(sessionCode, intent, territory)
    }

    override fun sendLogout(sessionCode: String): Observable<Boolean> {
        return dataManager().juliaApi().sendLogout(sessionCode)
    }
}