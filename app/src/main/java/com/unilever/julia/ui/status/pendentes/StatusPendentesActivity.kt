package com.unilever.julia.ui.status.pendentes

import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.LoadView
import com.unilever.julia.ui.component.JuliaStatusCard
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.julia_status_pendentes_activity.*
import kotlinx.android.synthetic.main.julia_status_pendentes_content.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class StatusPendentesActivity : BaseViewActivity(), StatusPendentesView {

    @Inject
    lateinit var mPresenter : StatusPendentesPresenter<StatusPendentesView, StatusPendentesInteractor>

    lateinit var mSessionCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.julia_status_pendentes_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        val model = intent.getParcelableExtra<StatusPedidoModel.Item>(RedirectIntents.EXTRA_STATUS)
        mSessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)

        mPresenter.getPendentes(mSessionCode, model)
    }

    override fun getLoadContent(): View? {
        return loadContent
    }

    override fun getLoadView(): LoadView? {
        return juliaLoadView
    }

    override fun addJuliaStatusCardItems(items : MutableList<StatusPedidoModel.Item>) {
        contentJuliaStatusCardPendentesItems.removeAllViewsInLayout()

        for (it in items) {
            val card = JuliaStatusCard(this@StatusPendentesActivity, null, JuliaStatusCard.DISABLED_SHARE)
            card.setItem(it)
            card.setAbreviateTextClient()
            card.setListener(object : JuliaStatusCard.Listener {
                override fun onButtonDetailClick(item: StatusPedidoModel.Item) {
                    RedirectIntents.redirectStatusDetalheActivity(this@StatusPendentesActivity, item, mSessionCode)
                }
            })
            contentJuliaStatusCardPendentesItems.addView(card)
        }
    }
}