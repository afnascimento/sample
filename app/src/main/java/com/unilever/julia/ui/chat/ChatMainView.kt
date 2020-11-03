package com.unilever.julia.ui.chat

import com.unilever.julia.data.model.*
import com.unilever.julia.components.model.IComponentsModel
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.IpvModel
import com.unilever.julia.ui.base.BaseView

interface ChatMainView : BaseView { //} AutenticacaoView {

    //fun showContentMsgInicioLoad(show: Boolean)
    //fun showContentMsgInicio(show: Boolean)
    fun scrollToBottom()
    fun showJuliaLoad()
    fun hideJuliaLoad()
    fun hideKeyboard()
    fun addItemChat(it: IComponentsModel)
    fun clearEditText()
    fun textToSpeech(text: String)
    fun redirectStatusPendentes(item: StatusPedidoModel.Item, sessionCode: String)
    fun redirectStatusDetalhe(item: StatusPedidoModel.Item, sessionCode: String)
    fun setUsername(username: String)
    fun navegarDetalheAgenda(item: AgendaModel.Item, sessionCode: String)
    fun redirectAgendaReuniaoActivity(sessionCode: String, context: AgendaOutlookContext)
    fun sendShareText(title: String, text: String)
    fun removeItemAgenda()
    fun showBottomInputText()
    fun hideBottomInputText()
    fun showButtonCloseInputText(hintText: String)
    fun hideButtonCloseInputText(hintText: String)
    fun redirectClientesActivity(sessionCode: String, intention: String, territory: String)
    fun disableScroll()
    fun enableScroll()
    fun showAutoCompleteTerritories(model: AutoCompleteTerritoryModel)
    fun showAutoCompleteCustomers(model: AutoCompleteCustomerModel)
    fun hideAutoCompleteTerritories()
    fun hideAutoCompleteCustomers()
    fun showContentBottom()
    fun hideContentBottom()
    fun showOverlay()
    fun hideOverlay()
    fun redirectNotifications(sessionCode: String)
    fun redirectConfiguration()
    fun redirectLogout()
    fun redirectActivityLogout()
    fun showButtonPushAlert()
    fun hideButtonPushAlert()
    fun showMenuPushMessage()
    fun hideMenuPushMessage()
    fun updateCountMenuPushMessages(count: Int)
    fun redirectIpvActivity(ipvContext: IpvContext, items: List<IpvItem>)
    fun redirectManagementActivity(ipvContext: IpvContext)
}