package com.unilever.julia.data.api

import com.unilever.julia.data.model.*
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersRequest
import com.unilever.julia.data.model.bulletin.GetBulletinRequest
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface JuliaUnilverEndpoints {

    @POST("/conversations/{sessionCode}")
    fun sendRequestBody(@Path("sessionCode") sessionCode : String, @Body body: RequestBody): Observable<Conversations>

    @POST("/conversations/")
    fun sendContextRequest(@Body body: ContextRequest): Call<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendContextRequest(@Path("sessionCode") sessionCode : String, @Body body: ContextRequest): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun getListaInovacoes(@Path("sessionCode") sessionCode : String, @Body send: SendListaInovacoes): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendAdicionarAgenda(@Path("sessionCode") sessionCode : String, @Body send: SendAdicionarAgenda): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendAgendaStatusChange(@Path("sessionCode") sessionCode : String, @Body send: AgendaStatusChange): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendStatusPedido(@Path("sessionCode") sessionCode : String, @Body send: SendStatusPedido): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendPedidoCliente(@Path("sessionCode") sessionCode : String, @Body send: SendPedidoCliente): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendReuniaoOutlookGravacao(@Path("sessionCode") sessionCode : String, @Body send: AgendaOutlookGravacao): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendReuniaoOutlookExcluir(@Path("sessionCode") sessionCode : String, @Body send: AgendaOutlookExcluir): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendFeedback(@Path("sessionCode") sessionCode : String, @Body send: FeedbackSend): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendSugestao(@Path("sessionCode") sessionCode : String, @Body send: SugestaoSend): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendCloseFlowInComponent(@Path("sessionCode") sessionCode : String, @Body send: CloseFlowSend): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendDisambiguation(@Path("sessionCode") sessionCode : String, @Body send: DisambiguationSend): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendCustomer(@Path("sessionCode") sessionCode : String, @Body send: CustomerSend): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendTerritory(@Path("sessionCode") sessionCode : String, @Body send: TerritorySend): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun getClientes(@Path("sessionCode") sessionCode : String, @Body request: GetClientesRequest): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun sendLogout(@Path("sessionCode") sessionCode : String, @Body request: GetLogoutRequest): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun getBulletinsMultipleFilters(@Path("sessionCode") sessionCode : String,
                                    @Body request: BulletinsMultipleFiltersRequest): Observable<Conversations>

    @POST("/conversations/{sessionCode}")
    fun getBulletinsByFilter(@Path("sessionCode") sessionCode : String,
                             @Body request: GetBulletinRequest): Observable<Conversations>
}