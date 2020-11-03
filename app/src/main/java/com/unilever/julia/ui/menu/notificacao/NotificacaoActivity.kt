package com.unilever.julia.ui.menu.notificacao

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.notificacao.Notification
import com.unilever.julia.data.model.notificacao.holder.NotificationViewType
import com.unilever.julia.ui.adapter.NotificationAdapter
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.notificacao_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class NotificacaoActivity : BaseViewActivity(), NotificacaoView, NotificationAdapter.Listener {

    @Inject
    lateinit var mPresenter: NotificacaoPresenter<NotificacaoView, NotificacaoInteractor>

    lateinit var mAdapter: NotificationAdapter

    lateinit var mSessionCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.notificacao_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        tvToolbarTitle.text = getString(R.string.novidades)

        toolbarBackArrow.setOnClickListener{ onBackPressed() }

        mAdapter = NotificationAdapter(this)
        rcviewNotificacoes.adapter = mAdapter
        rcviewNotificacoes.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        mSessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)
        mPresenter.getNotificacoes(mSessionCode)
    }

    override fun addItems(items: List<NotificationViewType>) {
        mAdapter.addItems(items)
    }

    override fun onItemClick(item: Notification, position: Int) {
        mPresenter.redirectItemNotification(item, position)
    }

    override fun getLoadContent(): View? {
        return rcviewNotificacoes
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    companion object {
        const val REQUEST_CODE_UPDATE = 1000
    }

    override fun redirectItemNotification(item: Notification) {
        RedirectIntents.redirectNotificationDetailActivity(this, REQUEST_CODE_UPDATE, item, mSessionCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_UPDATE) {
            if (data != null) {
                val notification = data.getParcelableExtra<Notification>(RedirectIntents.EXTRA_NOTIFICATION)
                mPresenter.updateNotificationRead(notification)
            }
        }
    }
}