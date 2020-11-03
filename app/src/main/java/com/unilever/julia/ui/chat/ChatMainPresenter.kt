package com.unilever.julia.ui.chat

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.data.model.ipv.IpvCardChat
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.adapter.ChatMainAdapter
import com.unilever.julia.ui.base.BasePresenter

interface ChatMainPresenter<V : ChatMainView, I : ChatMainInteractor> : BasePresenter<V, I> {

    fun notification()
    fun configuration()
    fun logout()
    //fun sendInitialConversation()
    fun sendTextConversation(text: String)
    //fun addConversationInView(conversations: Conversations)
    fun sendAdicionarAgenda(model: AdicionarAgendaModel.Item)
    fun sendTextConversation(text: String, showUserText: Boolean)
    fun saveTextToSpeech(enabled: Boolean)
    fun sendTextToSpeech(text: String)
    fun initConfiguration(notification : FirebaseNotification?)
    fun isTextToSpeech(): Boolean
    fun redirectStatusPedido(item: StatusPedidoModel.Item)
    fun agendaMudarStatusEvento(status: AgendaStatusEnum, item: AgendaModel.Item, removeListener: ChatMainAdapter.RemoveItemAgendaListener)
    fun navegarDetalheAgenda(item: AgendaModel.Item)
    fun addCardEdicaoAgenda(agenda: AgendaOutlookSucesso)
    fun onEdicaoAgendaOutlook(agenda: AgendaOutlookSucesso)
    fun compartilharPedidos(items: MutableList<StatusPedidoModel.Item>)
    fun eventoExcluido(text: String)
    fun eventoSalesForceExcluido()
    fun sendDislikeGetOptions()
    fun sendFeedback(text: String, intent: String)
    fun enviarExclusaoAgenda(idReuniao: String)
    fun onCloseInputText()
    fun onStatusCarteiraClickListener(intencao: String, territory: String)
    fun onCloseFlowInComponent(closeButton: ButtonClickListenerModel, showCloseMessage : Boolean)
    fun onButtonDisambiguationClickListener(intentButton: ButtonClickListenerModel,
                                            closeButton: ButtonClickListenerModel
    )
    fun sendCustomer(intent: String, model: Customer)
    fun sendTerritory(intent: String, model: Territory)
    fun sendCustomerText(intent: String, text: String)
    fun sendTerritoryText(intent: String, text: String)
    fun sendTextConversation(model: ButtonClickListenerModel)
    fun sendLogout()
    fun onReceivePushMessage(notification: FirebaseNotification)
    fun checkNewPushMessages()
    fun updateContentNewPushMessages(count: Int)
    fun onMenuOpen()
    fun onIpvDetailClick(model: IpvCardChat)
}