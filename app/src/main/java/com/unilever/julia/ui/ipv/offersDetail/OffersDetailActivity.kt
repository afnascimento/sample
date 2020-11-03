package com.unilever.julia.ui.ipv.offersDetail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.data.model.ipv.IpvOffer
import com.unilever.julia.data.model.ipv.IpvOfferProduct
import javax.inject.Inject
import com.unilever.julia.ui.base.*
import com.unilever.julia.ui.ipv.adapter.OffersProductsAdapter
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.ipv_offers_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class OffersDetailActivity : BaseViewActivity(), OffersDetailView {

    @Inject
    lateinit var mPresenter: OffersDetailPresenter<OffersDetailView, OffersDetailInteractor>

    private lateinit var mAdapter : OffersProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ipv_offers_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }
        tvToolbarTitle.text = getString(R.string.detalhe_ipv)

        mAdapter = OffersProductsAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.layoutManager = LinearLayoutManager(this@OffersDetailActivity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = mAdapter

        val ipvOffer: IpvOffer = intent.getParcelableExtra(RedirectIntents.EXTRA_IPV_OFFER)

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mPresenter.filterItems(s.toString())
            }
        })

        mPresenter.getIpvOffersProducts("", ipvOffer)
    }

    override fun getLoadContentViews(): List<View?>? {
        return listOf(contentCommodity, editText, recyclerView)
    }

    override fun getLoadView(): LoadView? {
        return loadView
    }

    override fun addItems(items: List<IpvOfferProduct>) {
        mAdapter.addItems(items)
    }

    override fun setIconOffer(icon: String) {
        buttonIconRound.setIcon(icon)
    }

    override fun setCommodity(commodity: String) {
        tvCommodity.text = commodity
    }

    override fun clearEditText() {
        editText.setText("", TextView.BufferType.EDITABLE)
    }

    override fun showContentViews() {
        contentCommodity.visibility = View.VISIBLE
        editText.visibility = View.VISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    override fun hideContentViews() {
        contentCommodity.visibility = View.GONE
        editText.visibility = View.GONE
        recyclerView.visibility = View.GONE
    }

    override fun visibleLoadView() {
        loadView.visibility = View.VISIBLE
    }

    override fun goneLoadView() {
        loadView.visibility = View.GONE
    }

    override fun showLoadViewEmpty() {
        contentCommodity.visibility = View.VISIBLE
        editText.visibility = View.VISIBLE
        loadView.showCustom(
            IconEnums.ICON_ERROR_FACE,
            getString(R.string.julia_load_empty_title),
            getString(R.string.julia_load_empty_text)
        )
    }

    override fun showLoadViewEmpty(title: String, text: String) {
        contentCommodity.visibility = View.VISIBLE
        editText.visibility = View.VISIBLE
        loadView.showCustom(
            IconEnums.ICON_ERROR_FACE,
            getString(R.string.julia_load_empty_filter_title_with_word, title),
            getString(R.string.julia_load_empty_filter_text)
        )
    }
}