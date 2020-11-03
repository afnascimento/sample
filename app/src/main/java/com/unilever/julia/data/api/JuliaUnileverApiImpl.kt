package com.unilever.julia.data.api

import com.google.gson.GsonBuilder
import com.unilever.julia.BuildConfig
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.certificate.ICertificateApi
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.data.model.bulletin.BulletinsMultipleFiltersRequest
import com.unilever.julia.data.model.bulletin.GetBulletinRequest
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.exception.ExternalServerOutException
import com.unilever.julia.exception.InternetConnectionException
import com.unilever.julia.exception.TimeoutConnectionException
import com.unilever.julia.logger.Logger
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.lang3.StringUtils
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class JuliaUnileverApiImpl(private val apiConfig : IJuliaUnileverApiConfig, certificateApi: ICertificateApi?) : JuliaUnileverApi {

    private val mGson = GsonBuilder()
        .registerTypeAdapter(ContextRequest::class.java, ContextRequest.Serializer())
        //.serializeNulls()
        //.setPrettyPrinting()
        .create()

    private val mApiEndpoints: JuliaUnilverEndpoints

    /*
    private val mCrypto : AESCrypt = AESCrypt(AndroidBase64())

    private var mUserRef : String? = null

    private fun getUserRef(): String {
        if (mUserRef == null) {
            mUserRef = mCrypto.encrypt(apiConfig.getEmail()) //StringUtils.removeEnd(mCrypto.encrypt(apiConfig.getEmail()), "\n")
        }
        return mUserRef ?: ""
    }

    private var mToken : String? = null

    private fun getToken(): String {
        if (mToken == null) {
            mToken = mCrypto.encrypt(apiConfig.getToken())// StringUtils.removeEnd(mCrypto.encrypt(apiConfig.getToken()), "\n")
        }
        return mToken ?: ""
    }
    */

    init {

        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                message -> Logger.debug("UNILEVER-API", message)
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()

        if (certificateApi != null) {
            builder.sslSocketFactory(certificateApi.getSSLSocketFactory(), certificateApi.getTrustManager())
        }

        val httpClient = builder
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()

                val request = original.newBuilder()
                    .header("API-KEY", apiConfig.getApiKey())
                    .header("PROJECT", apiConfig.getProject())
                    .header("CHANNEL", apiConfig.getChannel())
                    .header("OS", apiConfig.getOS())
                    .header("VERSION", BuildConfig.VERSION_NAME)
                    .header("USER-REF", StringUtils.lowerCase(apiConfig.getEmail()))
                    .header("TOKEN", apiConfig.getToken())
                    .header("Origin", "cd.unileverservices.com")
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("LOCALE", "pt-Br")
                    .header("Access-Control-Allow-Origin", apiConfig.getAccessControlAllowOrigin())
                    .method(original.method(), original.body())
                    .build()

                return@addInterceptor chain.proceed(request)
            }
            .build()

        mApiEndpoints = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(mGson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(apiConfig.getUrl())
            .client(httpClient)
            .build()
            .create(JuliaUnilverEndpoints::class.java)
    }

    companion object {
        const val TEXT = "text"
        const val CODE = "code"
        const val CONTEXT = "context"
    }

    private fun onNextConversationsHandlerError(observable: Observable<Conversations>): Observable<Conversations> {
        return observable
            .onErrorResumeNext(Function<Throwable, Observable<Conversations>> { error ->
                when (error) {
                    is UnknownHostException -> return@Function Observable.error(InternetConnectionException())
                    is SocketTimeoutException -> return@Function Observable.error(TimeoutConnectionException())
                    else -> return@Function Observable.error(error)
                }
            })
            .flatMap { conversations ->
                val answer = conversations.getAnswer()
                if (StringUtils.equalsIgnoreCase(answer.text, "error")) {
                    val apiException : ErrorApiException = mGson.fromJson(
                        answer.technicalText ?: "",
                        ErrorApiException::class.java
                    )
                    throw ExternalServerOutException(
                        apiException.error?.title ?: "",
                        apiException.error?.message ?: "",
                        apiException.error?.icon ?: "")
                }
                return@flatMap Observable.just(conversations)
            }
    }

    /*private fun encryptBody(request: Any): RequestBody {
        val json = mGson.toJson(request)
        val encrypt = mCrypto.encrypt(json)
        return RequestBody.create(MediaType.parse("text/plain"), encrypt)
    }*/

    /**
     * {"text":"oi"}
     * {"code":"CODE_01"}
     */
    override fun sendIntent(sessionCode : String, intent: JuliaIntent.Intent): Observable<Conversations> {
        val body = ContextRequest().addItem(intent.text, intent.value)
        return onNextConversationsHandlerError(mApiEndpoints.sendContextRequest(sessionCode, body))
        //val body = encryptBody(ContextRequest().addItem(intent.text, intent.value))
        //return onNextConversationsHandlerError(mApiEndpoints.sendRequestBody(sessionCode, body))
    }

    /**
     * {"text":"oi"}
     */
    override fun sendConversations(sessionCode: String, text: String): Observable<Conversations> {
        val body = ContextRequest().addItem(TEXT, text)
        return mApiEndpoints.sendContextRequest(sessionCode, body)
        //val body = encryptBody(ContextRequest().addItem(TEXT, text))
        //return onNextConversationsHandlerError(mApiEndpoints.sendRequestBody(sessionCode, body))
    }

    override fun getBulletinsMultipleFilters(sessionCode: String, attendanceFilter: AttendanceFilter): Observable<Conversations> {
        val request = BulletinsMultipleFiltersRequest()
        request.code = JuliaIntent.Intent.BOLETIM_MULTIPLE_FILTERS.value
        request.addTerritory(attendanceFilter.territory)
        request.addDistrict(attendanceFilter.district)
        request.addRegional(attendanceFilter.hitRegional)
        request.addFilial(attendanceFilter.subsidiary)

        return onNextConversationsHandlerError(mApiEndpoints.getBulletinsMultipleFilters(sessionCode, request))
    }

    override fun getBulletinsByFilter(sessionCode: String,
                                      territory: String,
                                      attendance : List<String>,
                                      brand : List<String>,
                                      category : List<String>,
                                      commodity : List<String>,
                                      customer : List<String>,
                                      channel : List<String>): Observable<Conversations> {
        val request = GetBulletinRequest()
        request.code = JuliaIntent.Intent.BOLETIM_BY_FILTER.value
        request.setTerritory(territory)
        request.addAttendance(attendance)
        request.addBrand(brand)
        request.addCategory(category)
        request.addCommodity(commodity)
        request.addCustomer(customer)
        request.addChannel(channel)
        return onNextConversationsHandlerError(mApiEndpoints.getBulletinsByFilter(sessionCode, request))
    }

    override fun getIpvConversations(sessionCode: String, intentCode: String, route: String): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, intentCode)
            .setNameContext(CONTEXT)
            .addContextItem("value", route)
        return onNextConversationsHandlerError(mApiEndpoints.sendContextRequest(sessionCode, body))
    }

    override fun sendNotificationRead(sessionCode: String, notificationId: String): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, JuliaIntent.Intent.NOTIFICACOES_LIDO_02.value)
            .setNameContext(CONTEXT)
            .addContextItem("id", notificationId)
        return mApiEndpoints.sendContextRequest(sessionCode, body)
    }

    override fun getNotificationDetail(sessionCode: String, notificationId: String): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, JuliaIntent.Intent.NOTIFICACOES_DETALHE_03.value)
            .setNameContext(CONTEXT)
            .addContextItem("id", notificationId)
        return mApiEndpoints.sendContextRequest(sessionCode, body)
    }

    override fun getProjectsInnovationsV2(
        sessionCode: String,
        code: String,
        categoriaId: String,
        commodityId: String,
        marcaId: String
    ): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, code)
            .setNameContext(CONTEXT)
            .addContextItem("categoria", categoriaId)
            .addContextItem("commodity", commodityId)
            .addContextItem("marcas", marcaId)
            .addContextItem("projeto", "")
            .addContextItem("dataLancamento", "")
        return mApiEndpoints.sendContextRequest(sessionCode, body)
    }

    override fun getDetailInnovationProject(sessionCode: String, code: String, identifier: String): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, code)
            .setNameContext(CONTEXT)
            .addContextItem("identificador", identifier)
        return mApiEndpoints.sendContextRequest(sessionCode, body)
    }

    override fun sendDownloadTradeStory(sessionCode: String, code: String, identifier: String): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, code)
            .setNameContext(CONTEXT)
            .addContextItem("idInnovations", identifier)
        return mApiEndpoints.sendContextRequest(sessionCode, body)
    }

    override fun sendEvaluationTradeStory(sessionCode: String,
                                          code: String,
                                          identifier: String,
                                          stars: Int,
                                          description: String): Observable<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, code)
            .setNameContext(CONTEXT)
            .addContextItem("idInnovations", identifier)
            .addContextItem("stars", stars)
            .addContextItem("description", description)
        return mApiEndpoints.sendContextRequest(sessionCode, body)
    }

    override fun send31PreInicio(imeiDevice : String, tokenFirebase: String): Call<Conversations> {
        val body = ContextRequest()
            .addItem(CODE, JuliaIntent.Intent.PREINICIO_31.value)
            .setNameContext(CONTEXT)
            .addContextItem("imeiDevice", imeiDevice)
            .addContextItem("tokenDeviceFirebase", tokenFirebase)
            .addContextItem("typeDevice", JuliaIntent.Type.ANDROID.value)
        return mApiEndpoints.sendContextRequest(body)
    }

    override fun getProjectsInnovations(sessionCode: String, code: String, categoriaId: String, commodityId: String, marcaId: String): Observable<Conversations> {
        val send = SendListaInovacoes()
        send.code = code
        send.context = SendListaInovacoes.Context(marcaId, categoriaId, commodityId)
        return mApiEndpoints.getListaInovacoes(sessionCode, send)
    }

    override fun sendAdicionarAgenda(sessionCode: String, model: AdicionarAgendaModel.Item): Observable<Conversations> {
        val send = SendAdicionarAgenda()
        send.code = model.textAction
        send.context = SendAdicionarAgenda.Context(model.assunto, model.data)
        return mApiEndpoints.sendAdicionarAgenda(sessionCode, send)
    }

    override fun sendStatusPedido(parcel: SendStatusParcel): Observable<Conversations> {
        val send = SendStatusPedido()
        send.code = parcel.code
        send.context = SendStatusPedido.Context(parcel.customerId, parcel.orderId, parcel.statusPedido, parcel.idRegion)
        return mApiEndpoints.sendStatusPedido(parcel.sessionCode, send)
    }

    override fun sendAgendaStatusChange(sessionCode: String, status: AgendaStatusEnum, item: AgendaModel.Item): Observable<Conversations> {
        val send = AgendaStatusChange()
        send.code = status.code
        send.context = AgendaStatusChange.Context(item.id ?: "", item.activityDate ?: "", status.statusTask)
        return mApiEndpoints.sendAgendaStatusChange(sessionCode, send)
    }

    override fun getPedidosCliente(model: PedidosClienteModel): Observable<MutableList<PedidoClienteResponse>> {
        val send = SendPedidoCliente()
        send.code = model.code
        send.context = SendPedidoCliente.Context(model.customerId)

        return mApiEndpoints.sendPedidoCliente(model.sessionCode, send)
            .flatMap { conversations ->
                val answer = conversations.getAnswer()

                val items = mGson.fromJson(answer.technicalText ?: "", Array<PedidoClienteResponse>::class.java).toMutableList()

                return@flatMap Observable.just(items)
            }
    }

    override fun sendReuniaoOutlookGravacao(sessionCode: String, send : AgendaOutlookGravacao): Observable<Conversations> {
        return mApiEndpoints.sendReuniaoOutlookGravacao(sessionCode, send)
    }

    override fun sendReuniaoOutlookExcluir(sessionCode: String, send : AgendaOutlookExcluir): Observable<Conversations> {
        return mApiEndpoints.sendReuniaoOutlookExcluir(sessionCode, send)
    }

    override fun sendFeedback(sessionCode: String, code: String, text: String): Observable<Conversations> {
        val send = FeedbackSend()
        send.code = code
        send.context.text = text
        return mApiEndpoints.sendFeedback(sessionCode, send)
    }

    override fun sendSugestao(sessionCode: String, code: String, text: String): Observable<Conversations> {
        val send = SugestaoSend()
        send.code = code
        send.context.text = text
        return mApiEndpoints.sendSugestao(sessionCode, send)
    }

    override fun sendCloseFlowInComponent(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations> {
        val send = CloseFlowSend()
        send.code = model.intencao
        send.context.event = model.eventAnalytics
        send.context.param = model.paramAnalytics
        return mApiEndpoints.sendCloseFlowInComponent(sessionCode, send)
    }

    override fun sendDisambiguation(sessionCode: String, model: ButtonClickListenerModel): Observable<Conversations> {
        val send = DisambiguationSend()
        send.code = model.intencao
        send.context.event = model.eventAnalytics
        send.context.param = model.paramAnalytics
        return mApiEndpoints.sendDisambiguation(sessionCode, send)
    }

    override fun sendCustomer(sessionCode: String, intent: String, customerId : String): Observable<Conversations> {
        val send = CustomerSend()
        send.code = intent
        send.context.customerId = customerId
        return mApiEndpoints.sendCustomer(sessionCode, send)
    }

    override fun sendTerritory(sessionCode: String, intent: String, territory : String): Observable<Conversations> {
        val send = TerritorySend()
        send.code = intent
        send.context.territory = territory
        return mApiEndpoints.sendTerritory(sessionCode, send)
    }

    override fun getClientes(sessionCode: String, intention: String, territory: String): Observable<MutableList<ClienteModel>> {
        val request = GetClientesRequest()
        request.code = intention
        request.context.territory = territory

        return mApiEndpoints.getClientes(sessionCode, request)
            .flatMap { conversations ->
                val answer = conversations.getAnswer()

                val items = mGson.fromJson(answer.technicalText ?: "", Array<ClienteModel>::class.java).toMutableList()

                return@flatMap Observable.just(items)
            }
    }

    override fun sendLogout(sessionCode: String): Observable<Boolean> {
        val request = GetLogoutRequest()
        request.code = "00_LOGOUT"
        return mApiEndpoints.sendLogout(sessionCode, request)
            .flatMap {
                return@flatMap Observable.just(true)
            }
    }
}