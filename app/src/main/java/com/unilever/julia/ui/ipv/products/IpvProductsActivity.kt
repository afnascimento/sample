package com.unilever.julia.ui.ipv.products

import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.IpvRecylerFilter
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvProduct
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.ipv_products_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class IpvProductsActivity : BaseViewActivity(), IpvProductsView {

    @Inject
    lateinit var mPresenter: IpvProductsPresenter<IpvProductsView, IpvProductsInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ipv_products_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        tvToolbarTitle.text = getString(R.string.detail_group)

        recylerFilter.setHint(getString(R.string.buscar_produto))

        val mIpvClient : IpvClient = intent.getParcelableExtra(RedirectIntents.EXTRA_IPV_CLIENT)
        val mIpvContext : IpvContext = intent.getParcelableExtra(RedirectIntents.EXTRA_IPV_CONTEXT)
        val mHeaderDescription = intent.getStringExtra(RedirectIntents.EXTRA_IPV_HEADER_DESC)

        mPresenter.init("", mIpvClient, mIpvContext, mHeaderDescription)
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return recylerFilter
    }

    override fun setHeaderButtonColor(color: String) {
        ipvHeaderClient.setButtonColor(color)
    }

    override fun setHeaderCommodity(commodity: String) {
        ipvHeaderClient.setCommodity(commodity)
    }

    override fun setHeaderCode(code: String) {
        ipvHeaderClient.setCode(code)
    }

    override fun setHeaderName(name: String) {
        ipvHeaderClient.setName(name)
    }

    override fun setHeaderDescription(description: String) {
        ipvHeaderClient.setDescription(description)
    }

    override fun addItems(items: List<IpvProduct>) {
        recylerFilter.addItems(items, object : IpvRecylerFilter.Adapter.Listener<IpvProduct> {
            override fun onClick(item: IpvProduct) {
            }
        })
    }
}