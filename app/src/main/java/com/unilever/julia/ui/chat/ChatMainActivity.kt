package com.unilever.julia.ui.chat

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.unilever.julia.R
import com.unilever.julia.broadcast.BroadcastConstants
import com.unilever.julia.broadcast.PushMessageBroadcast
import com.unilever.julia.components.ButtonAlertPush
import com.unilever.julia.components.boletim.AttendanceFilter
import com.unilever.julia.components.boletim.BoletimCardChat
import com.unilever.julia.components.model.*
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.components.boletim.BoletimFiltros
import com.unilever.julia.data.model.ipv.IpvCardChat
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.logger.Logger
import com.unilever.julia.ui.adapter.ChatMainAdapter
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.ui.base.RecyclerViewOnItemTouchListener
import com.unilever.julia.ui.component.ChatButtonsEditText
import com.unilever.julia.ui.component.ChatMainButtonsShare
import com.unilever.julia.ui.component.ChatMainFeedback
import com.unilever.julia.ui.component.JuliaStatusChat
import com.unilever.julia.ui.component.autoComplete.AutoCompleteComponent
import com.unilever.julia.ui.component.autoComplete.AutoCompleteCustomer
import com.unilever.julia.ui.component.autoComplete.AutoCompleteTerritory
import com.unilever.julia.utils.MaterialDialog
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.Utils
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.chat_main_activity.*
import kotlinx.android.synthetic.main.chat_main_bottom_edittext.*
import kotlinx.android.synthetic.main.chat_main_scroll_items.*
import kotlinx.android.synthetic.main.chat_main_top.*
import kotlinx.android.synthetic.main.chat_main_top_content.*
import kotlinx.android.synthetic.main.chat_main_top_menu.*
import org.apache.commons.lang3.StringUtils
import java.util.*
import javax.inject.Inject

class ChatMainActivity :
    BaseViewActivity(),
    ChatMainView,
    ChatMainAdapter.Listener,
    TextToSpeech.OnInitListener,
    JuliaStatusChat.Listener,
    ChatMainButtonsShare.Listener,
    ChatMainFeedback.Listener,
    PushMessageBroadcast.Listener {

    @Inject
    lateinit var mPresenter : ChatMainPresenter<ChatMainView, ChatMainInteractor>

    lateinit var mRecyclerView: RecyclerView

    lateinit var mTextToSpeech: TextToSpeech

    lateinit var mRecycleItemTouchListener : RecyclerViewOnItemTouchListener

    lateinit var mAdapter : ChatMainAdapter

    private val mHandlerDisposable : AutoCompleteComponent.HandlerDisposable = object : AutoCompleteComponent.HandlerDisposable {
        override fun addDisposable(disposable: Disposable) {
            this@ChatMainActivity.addDisposable(disposable)
        }
    }

    private var menuBtnAlertPush : ButtonAlertPush? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_main_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        val mDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ){
            override fun onDrawerOpened(view: View) {
                super.onDrawerOpened(view)
                view.findViewById<View>(R.id.imgCloseMenu).setOnClickListener { v -> onMenuItemClicked(v) }

                val viewNotif = view.findViewById<View>(R.id.ic_notif)
                viewNotif.setOnClickListener { v -> onMenuItemClicked(v) }
                menuBtnAlertPush = viewNotif.findViewById(R.id.btnAlertPush)

                view.findViewById<View>(R.id.ic_config).setOnClickListener { v -> onMenuItemClicked(v) }
                view.findViewById<View>(R.id.contentMenuSair).setOnClickListener {
                        v -> v.postDelayed({ onMenuItemClicked(v) }, 100)
                }

                mPresenter.onMenuOpen()
            }
        }

        mDrawerToggle.isDrawerIndicatorEnabled = false

        drawer_layout.addDrawerListener(mDrawerToggle)

        mDrawerToggle.syncState()

        imgOpenMenu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        mTextToSpeech = TextToSpeech(this@ChatMainActivity, this@ChatMainActivity)
        mTextToSpeech.setPitch(1.0f)
        mTextToSpeech.setSpeechRate(1.0f)

        // adapter config
        mAdapter = ChatMainAdapter(this@ChatMainActivity, this@ChatMainActivity)
        mAdapter.setHasStableIds(true)
        mRecyclerView = rcViewItems
        mRecyclerView.adapter = mAdapter
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setItemViewCacheSize(20)
        val itemAnimator = mRecyclerView.itemAnimator
        if (itemAnimator is SimpleItemAnimator) {
            itemAnimator.supportsChangeAnimations = false
            itemAnimator.changeDuration = 0
        }
        val reverseLayout = true
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, reverseLayout)
        linearLayoutManager.stackFromEnd = false
        linearLayoutManager.reverseLayout = reverseLayout
        mRecyclerView.layoutManager = linearLayoutManager

        mRecycleItemTouchListener = RecyclerViewOnItemTouchListener()
        mRecyclerView.addOnItemTouchListener(mRecycleItemTouchListener)

        btnButtonsChat.setOnClickListener(object : ChatButtonsEditText.OnClickListener {
            override fun onMicrophoneClick() {
                getSpeechInput()
            }

            override fun onSendMessageClick() {
                mPresenter.sendTextConversation(editInputText.text.toString())
            }
        })

        btCloseInputText.setOnClickListener {
            mPresenter.onCloseInputText()
        }

        editInputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (StringUtils.isEmpty(s?.toString())) {
                    btnButtonsChat.setVisibilityButtons(ChatButtonsEditText.Type.MICROPHONE)
                } else {
                    btnButtonsChat.setVisibilityButtons(ChatButtonsEditText.Type.SEND_MESSAGE)
                }
            }
        })

        autoCompleteCustomers.addHandlerDisposable(mHandlerDisposable)
        autoCompleteCustomers.setListener(object : AutoCompleteCustomer.Listener {

            override fun onCloseAutoComplete(button: ButtonClickListenerModel) {
                onCloseFlowInComponent(button, true)
            }

            override fun onCustomerSelected(customer: Customer, intent: String) {
                mPresenter.sendCustomer(intent, customer)
            }

            override fun onTextSendCustomer(text: String, intent: String) {
                mPresenter.sendCustomerText(intent, text)
            }
        })

        autoCompleteTerritories.addHandlerDisposable(mHandlerDisposable)
        autoCompleteTerritories.setListener(object : AutoCompleteTerritory.Listener {

            override fun onCloseAutoComplete(button: ButtonClickListenerModel) {
                onCloseFlowInComponent(button, true)
            }

            override fun onTerritorySelected(territory : Territory, intent: String) {
                mPresenter.sendTerritory(intent, territory)
            }

            override fun onTextSendTerritory(text: String, intent: String) {
                mPresenter.sendTerritoryText(intent, text)
            }
        })

        val notification = intent.getParcelableExtra<FirebaseNotification>(RedirectIntents.EXTRA_NOTIFICATION)

        contentBottomShare.setListener(this)
        chatMainFeedback.setListener(this)
        mPresenter.initConfiguration(notification)
    }

    override fun onNewIntent(intent: Intent?) {
        val intencao : String = intent?.getStringExtra(RedirectIntents.EXTRA_INTENTION) ?: ""
        mPresenter.sendTextConversation(intencao)
        closeMenu()
    }

    override fun setUsername(username: String) {
        tvUsername.text = username
    }

    private val mHandler = Handler()

    private fun onMenuItemClicked(view: View) {
        when (view.id) {
            R.id.imgCloseMenu -> closeMenu()
            R.id.ic_notif -> {
                closeMenu()
                mHandler.postDelayed({
                    mPresenter.notification()
                }, 200)
            }
            R.id.ic_config -> {
                closeMenu()
                mHandler.postDelayed({
                    mPresenter.configuration()
                }, 200)
            }
            R.id.contentMenuSair -> {
                mPresenter.logout()
                closeMenu()
            }
        }
    }

    private fun closeMenu() {
        drawer_layout.closeDrawer(GravityCompat.START, true)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = mTextToSpeech.setLanguage(Locale.getDefault())

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Logger.error("TextToSpeechActivity", "Language not supported", null)
            }
        } else {
            Logger.error("TextToSpeechActivity", "Initialization failed", null)
        }
    }

    override fun textToSpeech(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    private fun getSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_SPEECH)
        } else {
            Toast.makeText(this, "your device don't support speech input", Toast.LENGTH_SHORT).show()
        }
    }

    private val pushMessageBroadcast = PushMessageBroadcast(this)

    override fun onReceivePushNotification(notification: FirebaseNotification) {
        mPresenter.onReceivePushMessage(notification)
    }

    override fun showButtonPushAlert() {
        btnAlert.visibility = View.VISIBLE
    }

    override fun hideButtonPushAlert() {
        btnAlert.visibility = View.GONE
    }

    override fun showMenuPushMessage() {
        menuBtnAlertPush?.visibility = View.VISIBLE
    }

    override fun hideMenuPushMessage() {
        menuBtnAlertPush?.visibility = View.GONE
    }

    override fun updateCountMenuPushMessages(count : Int) {
        menuBtnAlertPush?.setNumPushs(count)
    }

    override fun onResume() {
        registerReceiver(pushMessageBroadcast, IntentFilter(BroadcastConstants.ACTION_PUSH))
        mPresenter.checkNewPushMessages()
        super.onResume()
    }

    override fun onDestroy() {
        unregisterReceiver(pushMessageBroadcast)
        mTextToSpeech.stop()
        mTextToSpeech.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val REQUEST_CODE_SPEECH = 10
        private const val REQUEST_CODE_AGENDA = 20
        private const val REQUEST_CODE_PEDIDO_CLIENTE = 30
        private const val REQUEST_CODE_AGENDA_REUNIAO = 40
        private const val REQUEST_CODE_BOLETIM_FILTRO = 50
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SPEECH -> if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val text = result[0]
                mPresenter.sendTextConversation(text)
            }
            REQUEST_CODE_AGENDA -> if (resultCode == RESULT_OK) {
                mPresenter.eventoSalesForceExcluido()
            }
            REQUEST_CODE_PEDIDO_CLIENTE -> if (resultCode == RESULT_OK && data != null) {
                val response = data.getParcelableExtra<PedidoClienteResponse>(RedirectIntents.EXTRA_PEDIDO_CLIENTE)
                mPresenter.sendTextConversation("Status do Pedido ${StringUtils.trimToEmpty(response.numeroPedido)}", true)
            }
            REQUEST_CODE_AGENDA_REUNIAO -> if (resultCode == RESULT_OK && data != null) {
                val agenda = data.getParcelableExtra<AgendaOutlookSucesso>(RedirectIntents.EXTRA_AGENDA_OUTLOOK_SUCESSO)
                if (agenda == null) {
                    mPresenter.eventoExcluido(data.getStringExtra(RedirectIntents.EXTRA_AGENDA_OUTLOOK_EXCLUSAO))
                } else {
                    mPresenter.addCardEdicaoAgenda(agenda)
                }
            }
            REQUEST_CODE_BOLETIM_FILTRO -> if (resultCode == RESULT_OK && data != null) {
                val model : BoletimChatModel = data.getParcelableExtra(RedirectIntents.EXTRA_BOLETIMCHATMODEL)
                val filtros: BoletimFiltros = data.getParcelableExtra(RedirectIntents.EXTRA_BOLETIM_FILTROS)
                val attendance : AttendanceFilter = data.getParcelableExtra(RedirectIntents.EXTRA_ATTENDANCE_FILTER)
                boletimCard?.updateBind(model.card!!, attendance, filtros)
            }
        }
    }

    override fun removeItemAgenda() {
        mRemoveAgendaListener?.onFinish()
    }

    override fun scrollToBottom() {
        val position = 0
        mRecyclerView.postDelayed({ mRecyclerView.smoothScrollToPosition(position) }, 500)
    }

    override fun enableScroll() {
        mRecycleItemTouchListener.enableScroll()
        hideOverlay()
    }

    override fun disableScroll() {
        mRecycleItemTouchListener.disableScroll()
    }

    override fun showOverlay() {
        contentShadow.visibility = View.VISIBLE
    }

    override fun hideOverlay() {
        contentShadow.visibility = View.GONE
    }

    override fun addItemChat(it: IComponentsModel) {
        mAdapter.addItem(it)
    }

    override fun onJuliaButtonClickListener(text: String, url: String, intencao: String) {
        mPresenter.sendTextConversation(intencao, true)
    }

    override fun onChitChatClickListener(text: String, url: String, intencao: String) {
        Utils.openLinkInBrowser(this@ChatMainActivity, url)
    }

    override fun onInovacaoClickListener(item: InnovationCardVerticalModel.Item) {
        RedirectIntents.redirectInovacoesProjetosActivity(this@ChatMainActivity, item)
    }

    override fun onInovacaoClickListener(item: InnovationCardHorizontalModel.Item) {
        RedirectIntents.redirectInnovationsProjectsActivity(this@ChatMainActivity, item)
    }

    override fun onStatusPedidoClickListener(item: StatusPedidoModel.Item) {
        mPresenter.redirectStatusPedido(item)
    }

    override fun redirectStatusPendentes(item: StatusPedidoModel.Item, sessionCode: String) {
        RedirectIntents.redirectStatusPendentesActivity(this@ChatMainActivity, item, sessionCode)
    }

    override fun redirectStatusDetalhe(item: StatusPedidoModel.Item, sessionCode: String) {
        RedirectIntents.redirectStatusDetalheActivity(this@ChatMainActivity, item, sessionCode)
    }

    override fun onAgendaClickListener(item: AgendaModel.Item) {
    }

    var mRemoveAgendaListener : ChatMainAdapter.RemoveItemAgendaListener? = null

    override fun onAgendaDetalheEvento(item: AgendaModel.Item, removeListener: ChatMainAdapter.RemoveItemAgendaListener) {
        mRemoveAgendaListener = removeListener
        mPresenter.navegarDetalheAgenda(item)
    }

    override fun navegarDetalheAgenda(item: AgendaModel.Item, sessionCode: String) {
        RedirectIntents.redirectAgendaActivity(this@ChatMainActivity, item, REQUEST_CODE_AGENDA, sessionCode)
    }

    override fun onAgendaConcluirEvento(item: AgendaModel.Item, removeListener: ChatMainAdapter.RemoveItemAgendaListener) {
        MaterialDialog(this@ChatMainActivity)
            .setTitle(getString(R.string.agenda_concluir_evento))
            .setNegativeButton(getString(R.string.dialog_nao))
            .setPositiveButton(getString(R.string.dialog_sim),  MaterialDialog.OnClickListener {
                mPresenter.agendaMudarStatusEvento(AgendaStatusEnum.CONCLUIR, item, removeListener)
            })
            .show()
    }

    override fun onAgendaCancelarEvento(item: AgendaModel.Item, removeListener: ChatMainAdapter.RemoveItemAgendaListener) {
        MaterialDialog(this@ChatMainActivity)
            .setTitle(getString(R.string.agenda_cancelar_evento))
            .setNegativeButton(getString(R.string.dialog_nao))
            .setPositiveButton(getString(R.string.dialog_sim), MaterialDialog.OnClickListener {
                mPresenter.agendaMudarStatusEvento(AgendaStatusEnum.CANCELAR, item, removeListener)
            })
            .show()
    }

    override fun onAgendaExcluirEvento(item: AgendaModel.Item, removeListener: ChatMainAdapter.RemoveItemAgendaListener) {
        mRemoveAgendaListener = removeListener

        MaterialDialog(this@ChatMainActivity)
            .setTitle(getString(R.string.agenda_excluir_evento))
            .setNegativeButton(getString(R.string.dialog_nao))
            .setPositiveButton(getString(R.string.dialog_sim), MaterialDialog.OnClickListener {
                mPresenter.enviarExclusaoAgenda(item.id ?: "")
            })
            .show()
    }

    override fun onAdicionarAgendaClickListener(item: AdicionarAgendaModel.Item) {
        mPresenter.sendAdicionarAgenda(item)
    }

    override fun onPedidosClienteClickListener(item: PedidosClienteModel) {
        RedirectIntents.redirectPedidosClienteActivity(this, item, REQUEST_CODE_PEDIDO_CLIENTE)
    }

    override fun showJuliaLoad() {
        mAdapter.showJuliaLoad()
    }

    override fun hideJuliaLoad() {
        mAdapter.hideJuliaLoad()
    }

    override fun clearEditText() {
        runOnUiThread {
            editInputText.setText("", TextView.BufferType.EDITABLE)
        }
    }

    override fun hideKeyboard() {
        Utils.hideKeyboard(this, editInputText)
    }

    override fun onReuniaoOutlookClickListener(item: ReuniaoOutlookModel) {
        RedirectIntents.redirectAgendaReuniaoActivity(this, item.sessionCode, null, REQUEST_CODE_AGENDA_REUNIAO)
    }

    override fun onEdicaoAgendaOutlook(agenda: AgendaOutlookSucesso) {
        mPresenter.onEdicaoAgendaOutlook(agenda)
    }

    override fun redirectAgendaReuniaoActivity(sessionCode: String, context: AgendaOutlookContext) {
        RedirectIntents.redirectAgendaReuniaoActivity(this, sessionCode, context, REQUEST_CODE_AGENDA_REUNIAO)
    }

    override fun redirectNotifications(sessionCode: String) {
        RedirectIntents.redirectNotificationsActivity(this, sessionCode)
    }

    override fun redirectConfiguration() {
        RedirectIntents.redirectConfigurationActivity(this)
    }

    override fun redirectLogout() {
        mPresenter.sendLogout()
    }

    override fun redirectActivityLogout() {
        RedirectIntents.redirectLogout(this)
    }

    override fun addItemShare(item: StatusPedidoModel.Item) {
        contentBottomShare.addItem(item)
    }

    override fun removeItemShare(item: StatusPedidoModel.Item) {
        contentBottomShare.removeItem(item)
    }

    override fun visibleContentShare() {
        contentBottomShare.visibility = View.VISIBLE
        hideBottomInputText()
    }

    override fun hideContentShare() {
        contentBottomShare.visibility = View.GONE
        showBottomInputText()
    }

    override fun setCardComponent(component: JuliaStatusChat) {
        contentBottomShare.setComponent(component)
    }

    override fun onClickCancelShare() {
        hideContentShare()
    }

    override fun onConfirmShare(items: MutableList<StatusPedidoModel.Item>) {
        mPresenter.compartilharPedidos(items)
    }

    override fun sendShareText(title: String, text: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, title)
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(sharingIntent, title))

        contentBottomShare.reset()
        hideContentShare()
    }

    override fun onDislikeFeedback() {
        mPresenter.sendDislikeGetOptions()
    }

    override fun showContentBottom() {
        contentBottom.visibility = View.VISIBLE
    }

    override fun hideContentBottom() {
        contentBottom.visibility = View.GONE
    }

    override fun showButtonCloseInputText(hintText: String) {
        btCloseInputText.visibility = View.VISIBLE
        editInputText.hint = hintText
    }

    override fun hideButtonCloseInputText(hintText: String) {
        btCloseInputText.visibility = View.GONE
        editInputText.hint = hintText
    }

    override fun showBottomInputText() {
        showContentBottom()
        contentBottomEdittext.visibility = View.VISIBLE
        hideButtonCloseInputText(getString(R.string.edittext_chat_hint))
        hideAutoCompleteCustomers()
        hideAutoCompleteTerritories()
        enableScroll()
    }

    override fun hideBottomInputText() {
        contentBottomEdittext.visibility = View.GONE
    }

    override fun onFeedbackOptionClick(text: String, intencao: String) {
        hideBottomInputText()
        chatMainFeedback.setIntentFeedback(intencao)
        chatMainFeedback.show()
        scrollToBottom()
    }

    override fun onCancelFeedback() {
        showBottomInputText()
        chatMainFeedback.hide()
    }

    override fun onConfirmFeedback(text: String, intent: String) {
        showBottomInputText()
        chatMainFeedback.visibility = View.GONE
        mPresenter.sendFeedback(text, intent)
    }

    override fun onButtonDisambiguationClickListener(intentButton: ButtonClickListenerModel,
                                                     closeButton: ButtonClickListenerModel
    ) {
        mPresenter.onButtonDisambiguationClickListener(intentButton, closeButton)
    }

    override fun onCloseFlowInComponent(closeButton: ButtonClickListenerModel, showCloseMessage : Boolean) {
        mPresenter.onCloseFlowInComponent(closeButton, showCloseMessage)
    }

    override fun onButtonV2ClickListener(model: ButtonClickListenerModel) {
        mPresenter.sendTextConversation(model)
    }

    override fun onStatusCarteiraClickListener(intencao: String, territory: String) {
        mPresenter.onStatusCarteiraClickListener(intencao, territory)
    }

    private var boletimCard: BoletimCardChat? = null

    override fun onBoletimFiltros(card: BoletimCardChat,
                                  item: BoletimChatModel,
                                  attendanceFilter: AttendanceFilter?,
                                  filters: BoletimFiltros?) {
        boletimCard = card
        RedirectIntents.redirectBoletimMainActivity(this,
            item.card?.territory ?: "", attendanceFilter, filters, REQUEST_CODE_BOLETIM_FILTRO)
    }

    override fun redirectClientesActivity(sessionCode: String, intention: String, territory: String) {
        RedirectIntents.redirectClientesActivity(this, REQUEST_CODE_PEDIDO_CLIENTE, sessionCode, intention, territory)
    }

    override fun showAutoCompleteTerritories(model: AutoCompleteTerritoryModel) {
        hideBottomInputText()
        autoCompleteTerritories.setText("")
        autoCompleteTerritories.addItems(model.items)
        autoCompleteTerritories.setCloseButton(model.closeButton)
        autoCompleteTerritories.setIntent(model.intent)
        autoCompleteTerritories.visibility = View.VISIBLE
    }

    override fun hideAutoCompleteTerritories() {
        autoCompleteTerritories.visibility = View.GONE
    }

    override fun showAutoCompleteCustomers(model : AutoCompleteCustomerModel) {
        hideBottomInputText()
        autoCompleteCustomers.setText("")
        autoCompleteCustomers.addItems(model.items)
        autoCompleteCustomers.setCloseButton(model.closeButton)
        autoCompleteCustomers.setIntent(model.intent)
        autoCompleteCustomers.visibility = View.VISIBLE
    }

    override fun hideAutoCompleteCustomers() {
        autoCompleteCustomers.visibility = View.GONE
    }

    override fun onIpvDetailClick(model: IpvCardChat) {
        mPresenter.onIpvDetailClick(model)
    }

    override fun redirectIpvActivity(ipvContext: IpvContext, items: List<IpvItem>) {
        RedirectIntents.redirectIpvActivity(this, ipvContext, items)
    }

    override fun redirectManagementActivity(ipvContext: IpvContext) {
        RedirectIntents.redirectManagementActivity(this, ipvContext)
    }
}