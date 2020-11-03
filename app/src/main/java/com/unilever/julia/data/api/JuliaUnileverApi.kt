package com.unilever.julia.data.api

import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.components.boletim.AttendanceFilter
import io.reactivex.Observable
import retrofit2.Call

interface JuliaUnileverApi {

    fun sendIntent(sessionCode: String, intent: JuliaIntent.Intent): Observable<Conversations>

    fun sendConversations(sessionCode: String, text: String): Observable<Conversations>

    fun getProjectsInnovations(
        sessionCode: String,
        code: String,
        categoriaId: String,
        commodityId: String,
        marcaId: String
    ): Observable<Conversations>

    fun sendAdicionarAgenda(sessionCode: String, model: AdicionarAgendaModel.Item): Observable<Conversations>

    fun sendStatusPedido(parcel: SendStatusParcel): Observable<Conversations>

    fun sendAgendaStatusChange(sessionCode: String, status: AgendaStatusEnum, item: AgendaModel.Item): Observable<Conversations>

    fun getPedidosCliente(model: PedidosClienteModel): Observable<MutableList<PedidoClienteResponse>>

    fun sendReuniaoOutlookGravacao(sessionCode: String, send: AgendaOutlookGravacao): Observable<Conversations>

    fun sendReuniaoOutlookExcluir(sessionCode: String, send: AgendaOutlookExcluir): Observable<Conversations>

    fun sendFeedback(sessionCode: String, code: String, text: String): Observable<Conversations>

    fun sendSugestao(sessionCode: String, code: String, text: String): Observable<Conversations>

    fun sendDisambiguation(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations>

    fun getClientes(sessionCode: String, intention: String, territory: String): Observable<MutableList<ClienteModel>>

    fun sendCloseFlowInComponent(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations>

    fun sendCustomer(sessionCode: String, intent: String, customerId: String): Observable<Conversations>

    fun sendTerritory(sessionCode: String, intent: String, territory: String): Observable<Conversations>

    fun getProjectsInnovationsV2(
        sessionCode: String,
        code: String,
        categoriaId: String,
        commodityId: String,
        marcaId: String
    ): Observable<Conversations>

    fun getDetailInnovationProject(
        sessionCode: String,
        code: String,
        identifier: String
    ): Observable<Conversations>

    fun sendDownloadTradeStory(sessionCode: String, code: String, identifier: String): Observable<Conversations>

    fun sendEvaluationTradeStory(
        sessionCode: String,
        code: String,
        identifier: String,
        stars: Int,
        description: String
    ): Observable<Conversations>

    fun send31PreInicio(imeiDevice: String, tokenFirebase: String): Call<Conversations>

    fun sendLogout(sessionCode: String): Observable<Boolean>

    fun sendNotificationRead(sessionCode: String, notificationId: String): Observable<Conversations>

    fun getNotificationDetail(sessionCode: String, notificationId: String): Observable<Conversations>
    fun getIpvConversations(
        sessionCode: String,
        intentCode: String,
        route: String
    ): Observable<Conversations>

    fun getBulletinsMultipleFilters(
        sessionCode: String,
        attendanceFilter: AttendanceFilter
    ): Observable<Conversations>

    fun getBulletinsByFilter(
        sessionCode: String,
        territory: String,
        attendance: List<String>,
        brand: List<String>,
        category: List<String>,
        commodity: List<String>,
        customer: List<String>,
        channel: List<String>
    ): Observable<Conversations>
}