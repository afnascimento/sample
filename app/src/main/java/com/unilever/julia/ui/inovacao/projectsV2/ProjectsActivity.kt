package com.unilever.julia.ui.inovacao.projectsV2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.unilever.julia.R
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.ui.adapter.ProjectsAdapter
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.components.LoadView
import com.unilever.julia.ui.inovacao.projectsV2.fragment.ProjectsTabFragment
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.capitalizeAllText
import kotlinx.android.synthetic.main.innovation_projects_activity.*
import kotlinx.android.synthetic.main.innovation_projects_content.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class ProjectsActivity : BaseViewActivity(), ProjectsView, ProjectsAdapter.Listener {

    @Inject
    lateinit var mPresenter: ProjectsPresenter<ProjectsView, ProjectsInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.innovation_projects_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        val sessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)
        val code = intent.getStringExtra(RedirectIntents.EXTRA_CODE)
        val marcaId = intent.getStringExtra(RedirectIntents.EXTRA_MARCA_ID)
        val categoriaId = intent.getStringExtra(RedirectIntents.EXTRA_CATEGORIA_ID)
        val commodity : String = intent.getStringExtra(RedirectIntents.EXTRA_COMMODITY)

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        tvToolbarTitle.text = commodity.capitalizeAllText()

        mPresenter.getProjectsInnovations(sessionCode, code, marcaId, categoriaId, commodity)
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return loadContent
    }

    override fun initTabs(projects : MutableList<InnovationProjectsModel.Project>) {
        val adapter = TabsAdapter(supportFragmentManager)
        for (project in projects) {
            adapter.addFragment(ProjectsTabFragment.newInstance(projects.indexOf(project)), project.name ?: "")
        }
        viewPager.adapter = adapter
        viewPager.setCurrentItem(1, true)

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

    override fun onClickListener(item: InnovationProjectsModel.Item) {
        RedirectIntents.redirectInnovationsDetailProjectActivity(this, item)
    }
}