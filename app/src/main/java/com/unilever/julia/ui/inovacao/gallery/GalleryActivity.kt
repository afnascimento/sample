package com.unilever.julia.ui.inovacao.gallery

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.unilever.julia.R
import com.unilever.julia.data.model.inovacaoDetalhe.Produto
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.snapHelper.OnSnapPositionChangeListener
import com.unilever.julia.utils.snapHelper.SnapOnScrollListener
import com.unilever.julia.utils.snapHelper.attachSnapHelperWithListener
import kotlinx.android.synthetic.main.innovation_gallery_activity.*

class GalleryActivity : BaseViewActivity(), GalleryView {

    @Inject
    lateinit var mPresenter: GalleryPresenter<GalleryView, GalleryInteractor>

    private var isPageScrollToPosition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.innovation_gallery_activity)

        toolbarBack.setClickListenerBackIcon(View.OnClickListener { onBackPressed() })

        val mIndexBegin = intent.getIntExtra(RedirectIntents.EXTRA_GALLERY_INDEX, 0)
        val mProducts = intent.getParcelableArrayListExtra<Produto>(RedirectIntents.EXTRA_GALLERY_LIST)

        /*
        val mIndexBegin = 14

        val mProducts = arrayListOf<Produto>()
        for (i in 1..15) {
            mProducts.add(
                Produto(
                    "PRODUTO $i",
                    "PRODUTO $i",
                    "https://juliajobfunction.blob.core.windows.net/innovations/Novos_SKUS/imagem_G/EAN7891150065741.jpg")
            )
        }*/

        indicator.setNumPages(mProducts.size)
        indicator.setOnSurfaceCount(5)
        indicator.setRisingCount(2)

        val adapter = GalleryAdapter(this)
        adapter.addItems(mProducts)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.attachSnapHelperWithListener(
            PagerSnapHelper(),
            SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
            object : OnSnapPositionChangeListener {
                override fun onSnapPositionChange(position: Int) {
                    if (isPageScrollToPosition) {
                        if (position == mIndexBegin) {
                            isPageScrollToPosition = false
                            indicator.setCurrentPage(position)
                        }
                    } else {
                        indicator.onPageScrolled(position)
                    }
                }
            })

        if (mIndexBegin > 0) {
            isPageScrollToPosition = true
            recyclerView.smoothScrollToPosition(mIndexBegin)
        }
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun getLoadContent(): View? {
        return contentMain
    }
}