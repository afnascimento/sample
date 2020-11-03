package com.unilever.julia.ui.menu.notificacao.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.holder.AttachedViewType
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.adapter.NotificationDetailAdapter
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.notificacao_item_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class NotificacaoDetailActivity : BaseViewActivity(), NotificacaoDetailView, NotificationDetailAdapter.Listener {

    @Inject
    lateinit var mPresenter: NotificacaoDetailPresenter<NotificacaoDetailView, NotificacaoDetailInteractor>

    lateinit var mAdapter: NotificationDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notificacao_item_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        tvToolbarTitle.text = getString(R.string.notificacoes_detalhe)

        toolbarBackArrow.setOnClickListener { mPresenter.onBackPressed() }

        val sessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)
        val notification = intent.getParcelableExtra<Notification?>(RedirectIntents.EXTRA_ITEM_NOTIFICACAO)
        val firebaseNotification = intent.getParcelableExtra<FirebaseNotification?>(RedirectIntents.EXTRA_NOTIFICATION)

        mAdapter = NotificationDetailAdapter(this)
        rcviewNotificacoesDetail.adapter = mAdapter
        rcviewNotificacoesDetail.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mPresenter.initView(sessionCode, notification, firebaseNotification)
    }

    override fun onBackPressed() {
        mPresenter.onBackPressed()
    }

    override fun setResultCancelled() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun setResultOK(notification: Notification) {
        setResult(Activity.RESULT_OK, Intent().putExtra(RedirectIntents.EXTRA_NOTIFICATION, notification))
        finish()
    }

    override fun redirectChatMainActivity() {
        RedirectIntents.redirectChatMainActivity(this)
    }

    override fun addItems(items: List<AttachedViewType>) {
        mAdapter.addItems(items)
    }

    override fun getLoadView(): LoadView? {
        return notificacoesDetailLoadView
    }

    override fun getLoadContent(): View? {
        return rcviewNotificacoesDetail
    }
}