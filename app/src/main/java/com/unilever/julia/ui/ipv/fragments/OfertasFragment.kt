package com.unilever.julia.ui.ipv.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.IpvOfertasCard
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvOffer
import com.unilever.julia.data.model.ipv.IpvOffers
import com.unilever.julia.ui.ipv.IpvActivity
import com.unilever.julia.ui.ipv.IpvInteractor
import com.unilever.julia.ui.ipv.IpvPresenter
import com.unilever.julia.ui.ipv.IpvView
import com.unilever.julia.ui.ipv.adapter.OffersCommodityAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class OfertasFragment : Fragment(), OffersCommodityAdapter.Listener {

    private lateinit var mView: IpvView
    private lateinit var mPresenter: IpvPresenter<IpvView, IpvInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = (context as IpvActivity)
        mPresenter = (context as IpvActivity).mPresenter
    }

    private lateinit var loadView : LoadView

    private lateinit var ipvOfertasCard : IpvOfertasCard

    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.ipv_ofertas, container, false)
        loadView = view.findViewById(R.id.loadView)
        ipvOfertasCard = view.findViewById(R.id.ipvOfertasCard)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    private var mAdapter : OffersCommodityAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = OffersCommodityAdapter(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = mAdapter
        init()
    }

    private fun init() {
        val views = listOf(ipvOfertasCard, recyclerView)
        mView.addDisposable(
            mPresenter.getIpvOffers("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    mView.showLoadView(views, loadView)
                }
                .subscribeWith(object : DisposableObserver<IpvOffers>() {
                    override fun onNext(value: IpvOffers) {
                        update(value)
                    }

                    override fun onComplete() {
                        mView.hideLoadView(views, loadView)
                    }

                    override fun onError(e: Throwable) {
                        mView.onErrorHandler(views, loadView, e, LoadView.OnClickListener { init() })
                    }
                })
        )
    }

    private fun update(offers: IpvOffers) {
        ipvOfertasCard.setCordinatorName(offers.coordinator ?: "")
        ipvOfertasCard.setDistrictName(offers.districtName ?: "")
        ipvOfertasCard.setDistrictScore(offers.districtScore ?: 0.0, offers.districtColorCode ?: "")
        ipvOfertasCard.setVendorName(offers.vendorName ?: "")
        ipvOfertasCard.setVendorScore(offers.vendorScore ?: 0.0, offers.vendorColorCode ?: "")

        mAdapter?.addItems(offers.offers ?: emptyList())
    }

    override fun onItemClick(item: IpvOffer) {
        mView.onIpvOfferClick(item)
    }
}
