package com.unilever.julia.ui.tutorial

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.unilever.julia.R
import com.unilever.julia.data.model.News
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.snapHelper.OnSnapPositionChangeListener
import com.unilever.julia.utils.snapHelper.SnapOnScrollListener
import com.unilever.julia.utils.snapHelper.attachSnapHelperWithListener
import kotlinx.android.synthetic.main.tutorial_activity.*
import kotlinx.android.synthetic.main.tutorial_content.indicator
import kotlinx.android.synthetic.main.tutorial_content.recyclerView
import javax.inject.Inject

class TutorialActivity : BaseViewActivity(), TutorialView {

    @Inject
    lateinit var mPresenter : TutorialPresenter<TutorialView, TutorialInteractor>

    var mAdapter = TutorialAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tutorial_activity)

        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.attachSnapHelperWithListener(
            PagerSnapHelper(),
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    indicator.onPageScrolled(position)
                }
            })

        imgClose.setOnClickListener {
            mPresenter.saveTutorialFinish()
        }

        mPresenter.getNews()
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return contentMain
    }

    override fun addItems(items : MutableList<News.Item>) {
        indicator.setNumPages(items.size)
        indicator.setOnSurfaceCount(5)
        indicator.setRisingCount(2)
        mAdapter.addItems(items)
    }
}