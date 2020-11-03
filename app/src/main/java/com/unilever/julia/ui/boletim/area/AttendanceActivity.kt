package com.unilever.julia.ui.boletim.area

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unilever.julia.R
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.boletim.AttendanceModel
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.boletim_area_atend_activity.*
import kotlinx.android.synthetic.main.boletim_area_atend_content.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class AttendanceActivity : BaseViewActivity(), AttendanceView {

    @Inject
    lateinit var mPresenter: AttendancePresenter<AttendanceView, AttendanceInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boletim_area_atend_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        tvToolbarTitle.text = getString(R.string.attendance_title)

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        val myTerritory : String = intent.getStringExtra(RedirectIntents.EXTRA_TERRITORY)

        mPresenter.init(myTerritory)
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return loadContent
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun onReturnWithResult(attendance: AttendanceModel) {
        val intent = Intent()
        intent.putExtra(RedirectIntents.EXTRA_ATTENDANCE, attendance)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private var mTabsAdapter = TabsAdapter(supportFragmentManager)

    override fun initTabs() {
        mTabsAdapter.addFragment(AttendanceFragment.newInstance(AttendanceModel.Type.territory),
            getString(R.string.attendance_territory))
        mTabsAdapter.addFragment(AttendanceFragment.newInstance(AttendanceModel.Type.district),
            getString(R.string.attendance_district))
        mTabsAdapter.addFragment(AttendanceFragment.newInstance(AttendanceModel.Type.subsidiary),
            getString(R.string.attendance_subsidiary))
        mTabsAdapter.addFragment(AttendanceFragment.newInstance(AttendanceModel.Type.hitRegional),
            getString(R.string.attendance_hit_regional))

        viewPager.adapter = mTabsAdapter
        viewPager.setCurrentItem(0, true)

        tabLayout.setupWithViewPager(viewPager)
    }

    override fun updateSelectedAllFragments(attendance: AttendanceModel) {
        mTabsAdapter.updateSelected(attendance)
    }

    class TabsAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        private var listFragments : MutableList<AttendanceFragment> = arrayListOf()

        private var listTitles : MutableList<String> = arrayListOf()

        fun updateSelected(selected : AttendanceModel) {
            for (it in listFragments) {
                it.updateSelected(selected)
            }
        }

        fun addFragment(fragment: AttendanceFragment, title: String) {
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