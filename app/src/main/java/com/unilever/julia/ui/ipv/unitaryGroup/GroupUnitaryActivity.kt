package com.unilever.julia.ui.ipv.unitaryGroup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unilever.julia.R
import com.unilever.julia.components.IpvRecylerFilter
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.goods.Good
import com.unilever.julia.data.model.goods.IpvGoodsGroup
import com.unilever.julia.data.model.goods.IpvGoodsUnitary
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.ui.ipv.unitaryGroup.fragment.GroupFragment
import com.unilever.julia.ui.ipv.unitaryGroup.fragment.UnitaryFragment
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.group_unitary_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class GroupUnitaryActivity : BaseViewActivity(), GroupUnitaryView, IpvRecylerFilter.Adapter.Listener<IpvGoodsGroup> {

    @Inject
    lateinit var mPresenter: GroupUnitaryPresenter<GroupUnitaryView, GroupUnitaryInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.group_unitary_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        tvToolbarTitle.text = getString(R.string.produtos)

        ipvClient = intent.getParcelableExtra(RedirectIntents.EXTRA_IPV_CLIENT)
        val ipvContext : IpvContext = intent.getParcelableExtra(RedirectIntents.EXTRA_IPV_CONTEXT)
        description = intent.getStringExtra(RedirectIntents.EXTRA_IPV_HEADER_DESC)

        mPresenter.init("", ipvClient, ipvContext, description)
    }

    private lateinit var description : String

    private lateinit var ipvClient : IpvClient

    override fun onClick(item: IpvGoodsGroup) {
        mPresenter.onClick(ipvClient, item, description)
    }

    override fun redirectIpvProductsActivity(ipvClient: IpvClient, ipvContext: IpvContext, headerDescription: String) {
        RedirectIntents.redirectIpvProductsActivity(this, ipvClient, ipvContext, headerDescription)
    }

    override fun getLoadContentViews(): List<View?>? {
        return listOf(ipvHeaderClient, tabLayout, viewPager)
    }

    override fun getLoadView(): LoadView? {
        return loadView
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

    override fun addGoods(good: Good) {
        val adapter = TabsAdapter(
            supportFragmentManager
        )

        val groups : ArrayList<IpvGoodsGroup> = arrayListOf()
        groups.addAll(good.groups ?: listOf())
        adapter.addFragment(GroupFragment.newInstance(groups), "GRUPO")

        val unitaries : ArrayList<IpvGoodsUnitary> = arrayListOf()
        unitaries.addAll(good.unitary ?: listOf())
        adapter.addFragment(UnitaryFragment.newInstance(unitaries), "UNITÁRIO")

        viewPager.adapter = adapter
        viewPager.setCurrentItem(0, true)

        tabLayout.setupWithViewPager(viewPager)
    }

    class TabsAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        private var listFragments : MutableList<Fragment> = arrayListOf()

        private var listTitles = arrayListOf<String>()

        fun addFragment(fragment: Fragment, title: String) {
            listFragments.add(fragment)
            listTitles.add(title)
        }

        override fun getItem(position: Int): Fragment {
            return listFragments[position]
        }

        override fun getCount(): Int {
            return listFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listTitles[position]
        }
    }
}