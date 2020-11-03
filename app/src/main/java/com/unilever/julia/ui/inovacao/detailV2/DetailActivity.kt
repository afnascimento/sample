package com.unilever.julia.ui.inovacao.detailV2

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.R
import com.unilever.julia.data.model.InnovationProjectsModel
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.AppBarStateChangeListener
import com.unilever.julia.data.model.inovacaoDetalhe.*
import com.unilever.julia.glide.GlideImage
import com.unilever.julia.ui.inovacao.detailV2.fragment.resumo.ResumoFragment
import com.unilever.julia.ui.inovacao.detailV2.model.InnovationFragmentData
import com.unilever.julia.ui.inovacao.detailV2.fragment.delistados.DelistadosFragment
import com.unilever.julia.ui.inovacao.detailV2.fragment.execucao.ExecucaoFragment
import com.unilever.julia.ui.inovacao.detailV2.fragment.novosSkus.NovosSkusFragment
import com.unilever.julia.ui.inovacao.detailV2.fragment.resumo.ResumoFragmentView
import com.unilever.julia.ui.inovacao.detailV2.fragment.visibilidade.VisibilidadeFragment
import com.unilever.julia.ui.inovacao.detailV2.model.PairFragment
import com.unilever.julia.ui.inovacao.tradestoryEvaluation.TradeStoryEvaluationActivity
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.capitalizeAllText
import kotlinx.android.synthetic.main.innovation_detail_activity.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class DetailActivity : BaseViewActivity(), DetailView, InnovationFragmentData {

    @Inject
    lateinit var mPresenter: DetailPresenter<DetailView, DetailInteractor>

    lateinit var mModel : InnovationProjectsModel.Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.innovation_detail_activity)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        mModel = intent.getParcelableExtra(RedirectIntents.EXTRA_INNOVATION_DETAIL)
        tvToolbarTitle.visibility = View.GONE

        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.EXPANDED -> {
                        tvToolbarTitle.visibility = View.GONE
                        toolbar.background = null
                    }
                    State.IDLE -> {
                        tvToolbarTitle.visibility = View.GONE
                        toolbar.background = null
                    }
                    State.COLLAPSED -> {
                        tvToolbarTitle.visibility = View.VISIBLE
                        toolbar.background = ContextCompat.getDrawable(this@DetailActivity, R.drawable.header_azul)
                    }
                }
            }
        })

        mPresenter.getDetail(this, mModel)
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContentViews(): MutableList<View?>? {
        return arrayListOf(contentLayout)
    }

    override fun setTitleToolbar(title: String) {
        tvToolbarTitle.text = title.capitalizeAllText()
    }

    override fun setTitle(title: String) {
        tvGeralTitle.text = title.capitalizeAllText()
    }

    override fun setImageCardLogo(url : String) {
        GlideImage.into(this, url, cardLogo.getImageView())
    }

    override fun setImageBanner(url: String, @DrawableRes placeholder : Int) {
        GlideImage.into(this, url, imgBanner, placeholder)
    }

    override fun getDrawableBrand(): Drawable {
        return mPresenter.getDrawableBrand()
    }

    private var mTabsAdapter : TabsAdapter? = null

    override fun initTabs(items : MutableList<PairFragment>) {
        mTabsAdapter = TabsAdapter(supportFragmentManager)
        mTabsAdapter?.addFragments(items)
        viewPager.adapter = mTabsAdapter
        viewPager.offscreenPageLimit = 1
        //viewPager.setCurrentItem(1, true)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun downloadTradestory(url: String) {
        mPresenter.putDownloadTradestory(mModel, url)
    }

    override fun startDownload(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onPlayerClick(intent: Intent) {
        startActivity(intent)
    }

    override fun redirectEvaluationTradestory() {
        val intent = Intent(this, TradeStoryEvaluationActivity::class.java)
        intent.putExtra(RedirectIntents.EXTRA_IDENTIFIER, mModel.identifier ?: "")
        startActivityForResult(intent, TradeStoryEvaluationActivity.REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TradeStoryEvaluationActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val tradeStory = data.getParcelableExtra<Resumo.TradeStory>(TradeStoryEvaluationActivity.EXTRA_TRADESTORY)
                mPresenter.updateTradestorySuccess(tradeStory)
            }
        }
    }

    override fun getResumo(): Resumo {
        return mPresenter.getResumo()
    }

    override fun getExecucao(): Execucao {
        return mPresenter.getExecucao()
    }

    override fun getVisibilidade(): Visibilidade {
        return mPresenter.getVisibilidade()
    }

    override fun getNovosSkus(): NovosSkus {
        return mPresenter.getNovosSkus()
    }

    override fun getSkusDeslistados(): SkusDeslistados {
        return mPresenter.getSkusDeslistados()
    }

    override fun onClickProductRedirectGallery(position : Int, products: List<Produto>) {
        RedirectIntents.redirectGalleryInnovations(this, position, products)
    }

    override fun resumeFragmentActual() {
        val fragment : Fragment? = mTabsAdapter?.getFragment(viewPager.currentItem)
        if (fragment != null && fragment is ResumoFragmentView) {
            fragment.updateTradeStory()
        }
    }

    class TabsAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) { // FragmentPagerAdapter(fm) {

        private var items : MutableList<PairFragment> = arrayListOf()

        private var mPageReferenceMap : MutableMap<Int, Fragment> = mutableMapOf()

        fun addFragments(items: MutableList<PairFragment>) {
            this.items = items
        }

        override fun getItem(position: Int): Fragment {
            return when (items[position].type) {
                PairFragment.Type.Resumo -> ResumoFragment()
                PairFragment.Type.Execucao -> ExecucaoFragment()
                PairFragment.Type.NovosSkus -> NovosSkusFragment()
                PairFragment.Type.SkusDeslistados -> DelistadosFragment()
                PairFragment.Type.Visibilidade -> VisibilidadeFragment()
            }
        }

        override fun getCount(): Int {
            return items.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return items[position].title
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            Log.d("DetailActivity", "instantiateItem() position -> $position")
            val fragment = super.instantiateItem(container, position)
            if (fragment is Fragment && !mPageReferenceMap.containsKey(position)) {
                mPageReferenceMap[position] = fragment
            }
            return fragment
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            Log.d("DetailActivity", "destroyItem() position -> $position")
            super.destroyItem(container, position, `object`)
            mPageReferenceMap.remove(position)
        }

        fun getFragment(position: Int): Fragment? {
            return mPageReferenceMap[position]
        }

        override fun getItemPosition(`object`: Any): Int {
            return PagerAdapter.POSITION_NONE
        }
    }
}