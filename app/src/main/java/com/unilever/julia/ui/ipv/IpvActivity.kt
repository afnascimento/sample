package com.unilever.julia.ui.ipv

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unilever.julia.R
import com.unilever.julia.components.IpvTabLayout
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.IpvType
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvOffer
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.ui.ipv.fragments.*
import com.unilever.julia.ui.ipv.fragments.PrioritiesFragment
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.ipv_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class IpvActivity : BaseViewActivity(), IpvView, IpvTabLayout.Listener {

    @Inject
    lateinit var mPresenter: IpvPresenter<IpvView, IpvInteractor>

    private lateinit var mAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ipv_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        setTitleToolbar(getString(R.string.detalhe_ipv))

        val items = intent.getParcelableArrayListExtra<IpvItem>(RedirectIntents.EXTRA_IPV_ITEMS)
        val context = intent.getParcelableExtra<IpvContext>(RedirectIntents.EXTRA_IPV_CONTEXT)
        mPresenter.init(context, items)
    }

    override fun redirectCategoriesPrioritiesActivity(ipvClient: IpvClient) {
        RedirectIntents.redirectCategoriesPrioritiesActivity(this, ipvClient)
    }

    override fun redirectGroupUnitaryActivity(ipvClient: IpvClient, ipvContext: IpvContext, headerDescription: String) {
        RedirectIntents.redirectGroupUnitaryActivity(this, ipvClient, ipvContext, headerDescription)
    }

    override fun onClientFocusClick(ipvClient: IpvClient) {
        mPresenter.onClientFocusClick(ipvClient)
    }

    override fun onClientPrioritiesClick(ipvClient: IpvClient) {
        mPresenter.onClientPrioritiesClick(ipvClient)
    }

    override fun onIpvOfferClick(item: IpvOffer) {
        RedirectIntents.redirectIpvOffersDetailActivity(this, item)
    }

    override fun onClientInnovationClick(ipvClient: IpvClient) {
        mPresenter.onClientInnovationClick(ipvClient)
    }

    override fun redirectIpvProductsActivity(ipvClient: IpvClient, ipvContext: IpvContext, headerDescription: String) {
        RedirectIntents.redirectIpvProductsActivity(this, ipvClient, ipvContext, headerDescription)
    }

    override fun setTitleToolbar(text: String) {
        tvToolbarTitle.text = text
    }

    override fun addTabs(tabs: List<IpvTabLayout.Item>) {
        ipvTabLayout.addTabs(tabs)
        ipvTabLayout.setupWithViewPager(viewPager)
        ipvTabLayout.setListener(this)
    }

    override fun onChangeTab(position: Int) {
        when (mAdapter.getType(position)) {
            IpvType.FASEAMENTO -> setTitleToolbar(getString(R.string.detalhe_ipv))
            IpvType.POSITIVACAO -> setTitleToolbar(getString(R.string.detalhe_ipv))
            IpvType.CATEGORIA_FOCO -> setTitleToolbar(getString(R.string.detalhe_ipv))
            IpvType.OFERTAS -> setTitleToolbar(getString(R.string.detalhe_ipv))
            IpvType.PRIORITARIOS -> setTitleToolbar(getString(R.string.clientes))
            IpvType.INOVACAO -> setTitleToolbar(getString(R.string.detalhe_ipv))
        }
    }

    override fun addPages(pages: List<IpvType>) {
        // config adapter
        mAdapter = ViewPagerAdapter(supportFragmentManager)
        mAdapter.addTypes(pages)
        //config view pager
        viewPager.adapter = mAdapter
        //viewPager.offscreenPageLimit = 1
    }

    class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) { // FragmentStatePagerAdapter(manager) {

        private var mItems : List<IpvType> = emptyList()

        //private var mPageReferenceMap : MutableMap<Int, Fragment> = mutableMapOf()

        fun getType(position: Int): IpvType {
            return mItems[position]
        }

        fun addTypes(items: List<IpvType>) {
            this.mItems = items
        }

        override fun getItem(position: Int): Fragment {
            return when (mItems[position]) {
                IpvType.FASEAMENTO -> FaseamentoFragment()
                IpvType.POSITIVACAO -> PositivacaoFragment()
                IpvType.OFERTAS -> OfertasFragment()
                IpvType.CATEGORIA_FOCO -> CategoriaFocoFragment()
                IpvType.PRIORITARIOS -> PrioritiesFragment()
                IpvType.INOVACAO -> InovacaoFragment()
            }
        }

        override fun getCount(): Int {
            return mItems.size
        }

        /*
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position)
            if (fragment is Fragment && !mPageReferenceMap.containsKey(position)) {
                mPageReferenceMap[position] = fragment
            }
            return fragment
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
            mPageReferenceMap.remove(position)
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
        */
    }
}