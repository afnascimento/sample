package com.unilever.julia.ui.ipv.categoriesPriorities

import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.IpvRecylerFilter
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvCategory
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.categories_priorities_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class CategoriesPrioritiesActivity : BaseViewActivity(),
    CategoriesPrioritiesView, IpvRecylerFilter.Adapter.Listener<IpvCategory> {

    @Inject
    lateinit var mPresenter: CategoriesPrioritiesPresenter<CategoriesPrioritiesView, CategoriesPrioritiesInteractor>

    private lateinit var mClient: IpvClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.categories_priorities_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        tvToolbarTitle.text = getString(R.string.categorias)

        recylerFilter.setHint(getString(R.string.buscar_categorias))

        mClient = intent.getParcelableExtra(RedirectIntents.EXTRA_IPV_CLIENT)
        mPresenter.init("", mClient)
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

    override fun addItems(items: List<IpvCategory>) {
        recylerFilter.addItems(items, this)
    }

    override fun onClick(item: IpvCategory) {
        mPresenter.onClick(mClient, item)
    }

    override fun redirectGroupUnitaryActivity(ipvClient: IpvClient, ipvContext: IpvContext, headerDescription: String) {
        RedirectIntents.redirectGroupUnitaryActivity(this, ipvClient, ipvContext, headerDescription)
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return recylerFilter
    }
}