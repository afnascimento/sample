package com.unilever.julia.ui.chat

import com.google.gson.Gson
import com.unilever.julia.R
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.enums.StatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.data.model.ipv.IpvCardChat
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.parser.*
import com.unilever.julia.firebase.FirebaseCrashApp
import com.unilever.julia.firebase.parser.ActionTextNotification
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.adapter.ChatMainAdapter
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.ui.base.IOnSubscribe
import com.unilever.julia.utils.MaterialDialog
import com.unilever.julia.utils.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChatMainPresenterImpl<V : ChatMainView, I : ChatMainInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), ChatMainPresenter<V, I> {

    private val mGson = Gson()

    private var mSessionCode = ""

    private var mIsTextToSpeech = false

    override fun isTextToSpeech(): Boolean {
        return mIsTextToSpeech
    }

    override fun notification() {
        getView().redirectNotifications(mSessionCode)
    }

    override fun configuration() {
        getView().redirectConfiguration()
    }

    override fun logout() {
        getView().redirectLogout()
    }

    private fun toObservablePair(observable : Observable<Conversations>): Observable<PairParserModel> {
        return toObservablePair(null, observable)
    }

    private fun toObservablePair(closeButton : ButtonClickListenerModel?, observable : Observable<Conversations>): Observable<PairParserModel> {
        return observable
            .flatMap { conversations ->
                mSessionCode = StringUtils.trimToEmpty(conversations.sessionCode)
                val type = ParserModelTypes.getType(conversations.getAnswer().title())
                val button = closeButton ?: ButtonClickListenerModel()
                return@flatMap parseConversations(type, conversations, button)
            }
    }

    override fun initConfiguration(notification : FirebaseNotification?) {
        val chatMain = getInteractor().getInitData()
        mIsTextToSpeech = chatMain.isTextToSpeech
        getView().setUsername(chatMain.user.givenName)

        if (notification != null && notification is ActionTextNotification) {
            sendTextConversation(notification.mTextChat)
            return
        }

        getView().addItemChat(
            ChatMessageInicioModel(chatMain.user.givenName, chatMain.banner, chatMain.bannerVisible)
        )

        getView().addDisposable(
            Observable.just(chatMain)
                .flatMap { data ->
                    if (data.countConfigVoice == 0) {
                        getInteractor().firebaseAnalytics().addEventVoiceSettingEnabled(data.isTextToSpeech)
                    }
                    FirebaseCrashApp.setLoggerUser(data.user)

                    return@flatMap toObservablePair(getInteractor().sendIntent(mSessionCode, JuliaIntent.Intent.PREINICIO_27))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        getView().scrollToBottom()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideJuliaLoad()
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    override fun sendTextConversation(model: ButtonClickListenerModel) {
        getInteractor().firebaseAnalytics().addEventParamsOriginBrokerLuis(
            model.eventAnalytics, model.paramAnalytics
        )
        sendTextConversation(model.intencao, true)
    }

    override fun sendTextConversation(text : String) {
        sendTextConversation(text, true)
    }

    override fun sendTextConversation(text: String, showUserText : Boolean) {
        var sendText = StringUtils.normalizeSpace(text)
        if (mEventsModel.isSendStatusPedido()) {
            sendText = "Status do Pedido $text"
        }

        val observable : Observable<Conversations>
        observable = if (mEventsModel.isSendSugestao()) {
            getInteractor().sendSugestao(mSessionCode, mEventsModel.getCodeSendSugestao(), sendText)
        } else {
            getInteractor().sendConversations(mSessionCode, sendText)
        }

        getView().addDisposable(
            toObservablePair(observable)
                //.delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    if (showUserText) {
                        addUserTextChat(sendText)
                        getView().clearEditText()
                    }
                    getView().hideKeyboard()
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        showBottomInputText()
                        getView().scrollToBottom()
                    }

                    override fun onError(e: Throwable) {
                        showBottomInputText()
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    private fun showBottomInputText() {
        if (mEventsModel.isSendStatusPedido()) {
            mEventsModel.disableSendStatusPedido()
            getView().showBottomInputText()
        }
    }

    private fun addUserTextChat(text: String) {
        getView().addItemChat(UserChatModel(text))
    }

    private fun addJuliaAnswerText(text: String, isCopyTextOnClick: Boolean) {
        getView().addItemChat(JuliaAnswerModel(text, isCopyTextOnClick))
    }

    private var numNewPushMessages = 0

    override fun onReceivePushMessage(notification: FirebaseNotification) {
        getView().addDisposable(
            getInteractor().getNewPushMessagesNotReads(notification)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onNext(value: Int) {
                        updateContentNewPushMessages(value)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun checkNewPushMessages() {
        getView().addDisposable(
            getInteractor().getNewPushMessagesNotReads()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onNext(value: Int) {
                        updateContentNewPushMessages(value)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun updateContentNewPushMessages(count: Int) {
        numNewPushMessages = count
        if (numNewPushMessages > 0)
            getView().showButtonPushAlert()
        else
            getView().hideButtonPushAlert()
    }

    override fun onMenuOpen() {
        when {
            numNewPushMessages > 0 -> {
                getView().showMenuPushMessage()
                getView().updateCountMenuPushMessages(numNewPushMessages)
            }
            else -> getView().hideMenuPushMessage()
        }
    }

    override fun sendAdicionarAgenda(model: AdicionarAgendaModel.Item) {
        if (!model.action) {
            addJuliaAnswerText(model.textAction, false)
            getView().scrollToBottom()
            return
        }

        getView().addDisposable(
            toObservablePair(getInteractor().sendAdicionarAgenda(mSessionCode, model))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    addUserTextChat(model.title)
                    getView().clearEditText()
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        getView().scrollToBottom()
                    }

                    override fun onError(e: Throwable) {
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    private fun onErrorShowMessage(e: Throwable?) {

        var message = getView().getContext().getString(R.string.error_padrao)
        if (e != null) {
            if (e is UnknownHostException) {
                message = getView().getContext().getString(R.string.error_sem_internet)
            } else if (e is SocketTimeoutException) {
                message = getView().getContext().getString(R.string.error_timeout)
            }
        }

        getView().hideJuliaLoad()
        addJuliaAnswerText(message, false)
        getView().scrollToBottom()
        sendTextToSpeech(message)
        getView().showBottomInputText()
    }

    override fun redirectStatusPedido(item: StatusPedidoModel.Item) {
        if (item.getStatusEnum() == StatusEnum.PENDENTE) {
            getView().redirectStatusPendentes(item, mSessionCode)
        } else {
            getView().redirectStatusDetalhe(item, mSessionCode)
        }
    }

    private fun parseConversations(type: ParserModelTypes,
                                   conversations: Conversations,
                                   closeButton : ButtonClickListenerModel): Observable<PairParserModel> {
        return parseConversationsWithType(type, conversations, closeButton)
            .flatMap { model ->
                return@flatMap Observable.just(PairParserModel(type, model))
            }
    }

    private fun parseConversationsWithType(type : ParserModelTypes,
                                           conversations: Conversations,
                                           closeButton : ButtonClickListenerModel): Observable<IParserModel> {
        when (type) {
            ParserModelTypes.BOLETIM_VENDAS -> return Observable.just(ParserBoletimChat(getView().getContext(), mGson, conversations))
            ParserModelTypes.BUTTON -> return Observable.just(ParserButton(conversations))
            ParserModelTypes.BUTTON_V2 -> return Observable.just(ParserButtonV2(conversations))
            ParserModelTypes.DISAMBIGUATION -> return Observable.just(ParserDisambiguation(conversations))
            ParserModelTypes.DISAMBIGUATION_MEU_TERRITORIO -> return Observable.just(ParserDisambiguationMeuTerritorio(mGson, conversations))
            ParserModelTypes.DISAMBIGUATION_OUTRO_TERRITORIO -> {
                 return getInteractor().getTerritories()
                    .flatMap { territories ->
                        return@flatMap Observable.just(ParserDisambiguationOutroTerritorio(conversations, territories, closeButton))
                    }
            }
            ParserModelTypes.DISAMBIGUATION_POR_CLIENTE -> {
                return getInteractor().getCustomers()
                    .flatMap { customers ->
                        return@flatMap Observable.just(ParserDisambiguationPorCliente(conversations, customers, closeButton))
                    }
            }
            ParserModelTypes.DISAMBIGUATION_COD_PEDIDO -> return Observable.just(ParserDisambiguationCodPedido(conversations))
            ParserModelTypes.CARD_INOVACAO -> return Observable.just(ParserCardInovacao(mGson, mSessionCode, conversations))
            ParserModelTypes.CARD_AGENDA -> return Observable.just(ParserCardAgenda(mGson, conversations))
            ParserModelTypes.CARD_IPV -> return Observable.just(ParserCardIpv(mGson, conversations, getInteractor().getUserLogged()))
            ParserModelTypes.AGENDA_OUTLOOK -> return Observable.just(ParserReuniaoOutlook(mSessionCode, conversations))
            ParserModelTypes.ADICIONAR_AGENDA -> return Observable.just(ParserAdicionarAgenda(conversations))
            ParserModelTypes.CARD_STATUS_PEDIDOS -> return Observable.just(ParserCardStatusPedido(mGson, conversations))
            ParserModelTypes.PEDIDOS_CLIENTE -> return Observable.just(ParserPedidosCliente(mGson, mSessionCode, conversations))
            ParserModelTypes.FEEDBACK -> return Observable.just(ParserFeedback(conversations))
            ParserModelTypes.SUGESTAO_ENVIO -> return Observable.just(ParserSugestaoEnvio(conversations))
            ParserModelTypes.SUGESTAO_RESPOSTA -> return Observable.just(ParserSugestaoResposta(conversations))
            ParserModelTypes.JULIA_ANSWER -> return Observable.just(ParserJuliaAnswer(true, conversations))
            ParserModelTypes.CHITCHAT -> return Observable.just(ParserChitChat(conversations))
            else -> return Observable.just(ParserChitChat(conversations))
        }
    }

    private fun addConversationInView(value: PairParserModel) {
        getView().hideJuliaLoad()

        val type : ParserModelTypes = value.type
        val parser : IParserModel = value.model

        when (type) {
            ParserModelTypes.BUTTON -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.BUTTON_V2 -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.DISAMBIGUATION -> {
                getView().addItemChat(parser.getModel())
                getView().hideBottomInputText()
                getView().disableScroll()
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.DISAMBIGUATION_MEU_TERRITORIO -> {
                val model : StatusPedidoCarteiraModel = value.model.getModel() as StatusPedidoCarteiraModel
                getView().addItemChat(model)
                getView().scrollToBottom()
                sendTextToSpeech(model.text)
            }
            ParserModelTypes.CARD_INOVACAO -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.CARD_AGENDA -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.CARD_IPV -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.AGENDA_OUTLOOK -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.ADICIONAR_AGENDA -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.CARD_STATUS_PEDIDOS -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.PEDIDOS_CLIENTE -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.FEEDBACK -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.SUGESTAO_ENVIO -> {
                val sugestaoModel : SugestaoModel = parser.getModel() as SugestaoModel
                mEventsModel.setCodeSendSugestao(sugestaoModel.code)
                addJuliaAnswerText(sugestaoModel.text, false)
                sendTextToSpeech(sugestaoModel.text)
            }
            ParserModelTypes.SUGESTAO_RESPOSTA -> {
                val sugestaoModel : SugestaoModel = parser.getModel() as SugestaoModel
                mEventsModel.clearCodeSendSugestao()
                addJuliaAnswerText(sugestaoModel.text, false)
                sendTextToSpeech(sugestaoModel.text)
            }
            ParserModelTypes.JULIA_ANSWER -> {
                addJuliaAnswerText(parser.getText(), true)
            }
            ParserModelTypes.CHITCHAT -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            ParserModelTypes.BOLETIM_VENDAS -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
            else -> {
                getView().addItemChat(parser.getModel())
                sendTextToSpeech(parser.getText())
            }
        }
    }

    private val mEventsModel = EventsModel()

    class EventsModel {

        private var mSugestaoCode : String? = null

        private var mCodigoStatusPedido = false

        private var mButtonCloseInputText = ButtonClickListenerModel()

        fun setButtonClose(closeButton: ButtonClickListenerModel) {
            mButtonCloseInputText = closeButton
        }

        fun isCloseButtonValid():Boolean {
            return StringUtils.isNotEmpty(mButtonCloseInputText.intencao)
        }

        fun clearButtonClose() {
            mButtonCloseInputText = ButtonClickListenerModel()
        }

        fun getButtonClose(): ButtonClickListenerModel {
            return mButtonCloseInputText
        }

        fun isSendStatusPedido(): Boolean {
            return mCodigoStatusPedido
        }

        fun enableSendStatusPedido() {
            mCodigoStatusPedido = true
        }

        fun disableSendStatusPedido() {
            mCodigoStatusPedido = false
        }

        fun getCodeSendSugestao() : String {
            return mSugestaoCode ?: ""
        }

        fun setCodeSendSugestao(code: String) {
            mSugestaoCode = code
        }

        fun clearCodeSendSugestao() {
            mSugestaoCode = null
        }

        fun isSendSugestao(): Boolean {
            return StringUtils.isNotBlank(mSugestaoCode)
        }

        fun clearAllEvents() {
            clearButtonClose()
            clearCodeSendSugestao()
            disableSendStatusPedido()
        }
    }

    override fun addCardEdicaoAgenda(agenda: AgendaOutlookSucesso) {
        agenda.context?.owner = true
        getView().addItemChat(agenda)
        getView().scrollToBottom()
    }

    override fun eventoSalesForceExcluido() {
        getView().removeItemAgenda()
    }

    override fun eventoExcluido(text: String) {
        getView().showToast(text)
        getView().removeItemAgenda()
    }

    override fun onEdicaoAgendaOutlook(agenda: AgendaOutlookSucesso) {
        getView().redirectAgendaReuniaoActivity(mSessionCode, agenda.context!!)
    }

    override fun sendTextToSpeech(text: String) {
        if (!mIsTextToSpeech) {
            return
        }

        val textToSpeech = Utils.getTextFromHtml(text).toString()

        if (StringUtils.isNotEmpty(textToSpeech)) {
            if (StringUtils.contains(textToSpeech, ":")) {
                val split = StringUtils.split(textToSpeech, ":")
                getView().textToSpeech(split[0])
            } else {
                getView().textToSpeech(textToSpeech)
            }
        }
    }

    override fun saveTextToSpeech(enabled: Boolean) {
        getView().addDisposable(
            getInteractor().saveTextToSpeech(enabled)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        mIsTextToSpeech = enabled
                        getInteractor().firebaseAnalytics().addEventVoiceSettingEnabled(enabled)
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    override fun agendaMudarStatusEvento(status: AgendaStatusEnum, item: AgendaModel.Item, removeListener: ChatMainAdapter.RemoveItemAgendaListener) {
        getView().addDisposable(
            getInteractor().sendAgendaStatusChange(mSessionCode, status, item)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showProgressDialog()
                }
                .subscribeWith(object : DisposableObserver<Conversations>() {
                    override fun onNext(conversations: Conversations) {
                        addJuliaAnswerText(conversations.getAnswer().text ?: "", false)
                    }

                    override fun onComplete() {
                        getView().hideProgressDialog()
                        removeListener.onFinish()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideProgressDialog()
                    }
                })
        )
    }

    override fun navegarDetalheAgenda(item: AgendaModel.Item) {
        if (item.isEventOutlook()) {
            val context = AgendaOutlookContext()
            context.id = item.outlookCalendar?.id
            context.assunto = item.outlookCalendar?.assunto
            context.dataInicio = item.outlookCalendar?.dataInicio
            context.dataFim = item.outlookCalendar?.dataFim
            context.horaInicio = item.outlookCalendar?.horaInicio
            context.horaFim = item.outlookCalendar?.horaFim
            context.local = item.outlookCalendar?.local
            context.owner = item.owner

            val lista = arrayListOf<AgendaOutlookParticipante>()
            if (!item.outlookCalendar?.participantes.isNullOrEmpty()) {
                for (part in item.outlookCalendar?.participantes!!) {
                    val email = StringUtils.remove(StringUtils.trimToEmpty(part.email), "\"")
                    lista.add(AgendaOutlookParticipante(email))
                }
            }
            context.participantes = lista

            getView().redirectAgendaReuniaoActivity(mSessionCode, context)
        } else {
            getView().navegarDetalheAgenda(item, mSessionCode)
        }
    }

    override fun compartilharPedidos(items: MutableList<StatusPedidoModel.Item>) {
        val context = getView().getContext()

        val item : StatusPedidoModel.Item = items[0]
        val builder = StringBuilder()
        builder.append("Resumo do pedido do Cliente ")
        builder.append(item.order?.descCustomer ?: "").append(":\n")
        builder.append("Pedido SAP ").append(item.order?.codPedido ?: "").append(" | ")
        builder.append("Cliente ").append(item.order?.idCustomerOrder ?: "").append(" | ")
        builder.append("CRM ").append(item.order?.idCrm ?: "").append("\n\n")

        for (it in items) {
            builder.append(it.getStatusEnum().getText(context)).append("\n")
            builder.append(it.order?.getValorPedido() ?: "").append("\n")
            builder.append(it.order?.amount ?: "0").append(" Caixa(s)").append("\n\n")
        }

        val text = StringUtils.removeEnd(builder.toString(), "\n\n")
        getView().sendShareText("Compartilhar Pedido", text)

        getInteractor().firebaseAnalytics().addEventOrderShared(true)
    }

    override fun sendDislikeGetOptions() {
        sendTextConversation("02_DISLIKE", false)
    }

    override fun sendFeedback(text: String, intent: String) {
        getView().addDisposable(
            toObservablePair(getInteractor().sendFeedback(mSessionCode, intent, text))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        getView().scrollToBottom()
                    }

                    override fun onError(e: Throwable) {
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    override fun enviarExclusaoAgenda(idReuniao: String) {
        enviarExclusaoAgenda(idReuniao, mSessionCode, object : IOnSubscribe<String> {
            override fun doOnSubscribe() {
                getView().showProgressDialog()
            }

            override fun onNext(value: String) {
                getView().removeItemAgenda()
            }

            override fun onComplete() {
                getView().hideProgressDialog()
            }

            override fun onError(e: Throwable) {
                getView().hideProgressDialog()
                getView().onErrorHandlerDialog(e, MaterialDialog.OnClickListener { dialog -> dialog.dismiss() })
            }
        })
    }

    override fun sendCustomer(intent: String, model: Customer) {
        sendCustomerText(intent, model.code ?: "")
    }

    override fun sendCustomerText(intent: String, text: String) {
        sendDisambiguation("Pedidos do cliente $text",
            getInteractor().sendCustomer(mSessionCode, intent, text)
        )
    }

    override fun sendTerritory(intent: String, model: Territory) {
        sendTerritoryText(intent, model.code ?: "")
    }

    override fun sendTerritoryText(intent: String, text: String) {
        sendDisambiguation("Pedidos do território $text",
            getInteractor().sendTerritory(mSessionCode, intent, text)
        )
    }

    private fun sendDisambiguation(userMessage: String, observable: Observable<Conversations>) {
        getView().addDisposable(
            toObservablePair(observable)
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().hideKeyboard()
                    getView().hideContentBottom()
                    getView().enableScroll()
                    addUserTextChat(userMessage)
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        getView().showBottomInputText()
                        getView().scrollToBottom()
                    }

                    override fun onError(e: Throwable) {
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    override fun onCloseFlowInComponent(closeButton: ButtonClickListenerModel, showCloseMessage: Boolean) {
        getView().addDisposable(
            toObservablePair(getInteractor().onCloseFlowInComponent(mSessionCode, closeButton))
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    if (showCloseMessage) {
                        addUserTextChat(closeButton.text)
                    }
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                    getView().hideContentBottom()
                    getView().hideOverlay()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        getView().scrollToBottom()
                        getView().showBottomInputText()
                        getInteractor().firebaseAnalytics().addEventParamsOriginBrokerLuis(
                            closeButton.eventAnalytics,
                            closeButton.paramAnalytics
                        )
                    }

                    override fun onError(e: Throwable) {
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    override fun onButtonDisambiguationClickListener(intentButton: ButtonClickListenerModel,
                                                     closeButton: ButtonClickListenerModel) {
        getView().addDisposable(
            toObservablePair(closeButton, getInteractor().sendDisambiguation(mSessionCode, intentButton))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {

                    private fun emptyMessage(emptyMessage: String) {
                        addJuliaAnswerText(emptyMessage, false)
                        sendTextToSpeech(emptyMessage)
                        getView().scrollToBottom()
                        getView().showBottomInputText()
                    }

                    override fun onNext(value: PairParserModel) {
                        getView().hideJuliaLoad()

                        when (value.type) {
                            ParserModelTypes.DISAMBIGUATION_MEU_TERRITORIO -> {
                                val model : StatusPedidoCarteiraModel = value.model.getModel() as StatusPedidoCarteiraModel
                                getView().addItemChat(model)
                                getView().scrollToBottom()
                                getView().showBottomInputText()
                                sendTextToSpeech(model.text)
                            }
                            ParserModelTypes.DISAMBIGUATION_OUTRO_TERRITORIO -> {
                                val model : AutoCompleteTerritoryModel = value.model.getModel() as AutoCompleteTerritoryModel

                                if (model.items.isNullOrEmpty()) {
                                    emptyMessage(getView().getContext().getString(R.string.territories_empty))
                                    return
                                }

                                addJuliaAnswerText(model.text, false)
                                getView().scrollToBottom()
                                getView().disableScroll()
                                getView().showOverlay()
                                getView().showAutoCompleteTerritories(model)
                                sendTextToSpeech(model.text)
                            }
                            ParserModelTypes.DISAMBIGUATION_POR_CLIENTE -> {
                                val model : AutoCompleteCustomerModel = value.model.getModel() as AutoCompleteCustomerModel

                                if (model.items.isNullOrEmpty()) {
                                    emptyMessage(getView().getContext().getString(R.string.customers_empty))
                                    return
                                }

                                addJuliaAnswerText(model.text, false)
                                getView().scrollToBottom()
                                getView().disableScroll()
                                getView().showOverlay()
                                getView().showAutoCompleteCustomers(model)
                                sendTextToSpeech(model.text)
                            }
                            ParserModelTypes.DISAMBIGUATION_COD_PEDIDO -> {
                                mEventsModel.enableSendStatusPedido()
                                mEventsModel.setButtonClose(closeButton)

                                val model : DisambiguationCodPedidoModel = value.model.getModel() as DisambiguationCodPedidoModel
                                addJuliaAnswerText(model.text, false)
                                getView().scrollToBottom()
                                getView().showBottomInputText()
                                getView().showButtonCloseInputText(getView().getContext().getString(R.string.hint_cod_pedido))
                                sendTextToSpeech(model.text)
                            }
                            else -> { }
                        }
                    }

                    override fun onComplete() {
                        getInteractor().firebaseAnalytics().addEventParamsOriginBrokerLuis(
                            intentButton.eventAnalytics,
                            intentButton.paramAnalytics
                        )
                    }

                    override fun onError(e: Throwable) {
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    override fun onCloseInputText() {
        if (!mEventsModel.isCloseButtonValid()) {
            return
        }

        getView().addDisposable(
            toObservablePair(getInteractor().sendDisambiguation(mSessionCode, mEventsModel.getButtonClose()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    addUserTextChat(mEventsModel.getButtonClose().text)
                    getView().showBottomInputText()
                    getView().showJuliaLoad()
                    getView().scrollToBottom()
                }
                .subscribeWith(object : DisposableObserver<PairParserModel>() {
                    override fun onNext(value: PairParserModel) {
                        addConversationInView(value)
                    }

                    override fun onComplete() {
                        mEventsModel.clearAllEvents()
                        getView().scrollToBottom()
                    }

                    override fun onError(e: Throwable) {
                        onErrorShowMessage(e)
                    }
                })
        )
    }

    override fun onStatusCarteiraClickListener(intencao: String, territory: String) {
        getView().redirectClientesActivity(mSessionCode, intencao, territory)
    }

    override fun sendLogout() {
        getView().addDisposable(
            getInteractor().sendLogout(mSessionCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                        getView().redirectActivityLogout()
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandlerDialog(e, MaterialDialog.OnClickListener { dialog -> dialog.dismiss() })
                    }
                })
        )
    }

    override fun onIpvDetailClick(model: IpvCardChat) {
        val isManagement = model.context?.isManagement() ?: false
        if (isManagement) {
            getView().redirectManagementActivity(model.context ?: IpvContext())
        } else {
            getView().redirectIpvActivity(model.context ?: IpvContext(), model.ipv?.items ?: emptyList())
        }
    }
}