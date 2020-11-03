package com.unilever.julia.ui.ipv.management

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.IpvManagementHolder
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.model.IpvManagementCard
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvItem
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.ui.ipv.adapter.ManagementAdapter
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.ipv_management_activity.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class ManagementActivity : BaseViewActivity(), ManagementView, IpvManagementHolder.Listener {

    @Inject
    lateinit var mPresenter: ManagementPresenter<ManagementView, ManagementInteractor>

    lateinit var mAdapter : ManagementAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ipv_management_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        setTitle(getString(R.string.detalhe_ipv))

        mAdapter = ManagementAdapter(this)
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.layoutManager = LinearLayoutManager(this@ManagementActivity, RecyclerView.VERTICAL, false)

        val ipvContext = intent.getParcelableExtra<IpvContext>(RedirectIntents.EXTRA_IPV_CONTEXT)

        mPresenter.getIpvManagement("", ipvContext)
    }

    override fun setTitle(title: String) {
        tvToolbarTitle.text = title
    }

    override fun setHintSearch(text: String) {
        editTextSearch.hint = text
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContentViews(): List<View?>? {
        return listOf(ipvManagementHeader, editTextSearch, recyclerView)
    }

    override fun setHeader(card: IpvManagementCard) {
        ipvManagementHeader.bind(card)
    }

    override fun addItems(items: List<IpvManagementCard>) {
        mAdapter.addItems(items)
    }

    override fun onClickNextContext(management: IpvManagementCard) {
        mPresenter.onClickNextContext(management)
    }

    override fun redirectIpvActivity(ipvContext: IpvContext, items: List<IpvItem>) {
        RedirectIntents.redirectIpvActivity(this, ipvContext, items)
    }

    override fun redirectManagementActivity(ipvContext: IpvContext) {
        RedirectIntents.redirectManagementActivity(this, ipvContext)
    }
}