package com.unilever.julia.ui.ipv.offersDetail

import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvOffer
import com.unilever.julia.data.model.ipv.IpvOfferProduct
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils

import javax.inject.Inject

class OffersDetailPresenterImpl<V : OffersDetailView, I : OffersDetailInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), OffersDetailPresenter<V, I> {

    override fun getIpvOffersProducts(sessionCode: String, ipvOffer : IpvOffer) {
        getView().addDisposable(
            getInteractor().getIpvOffersProducts(sessionCode, ipvOffer.context?.code ?: "", ipvOffer.context?.value ?: "")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<List<IpvOfferProduct>>() {
                    override fun onNext(value: List<IpvOfferProduct>) {
                        getView().setIconOffer(ipvOffer.icon ?: "")
                        getView().setCommodity(ipvOffer.commodity ?: "")
                        if (value.isNotEmpty()) {
                            getView().goneLoadView()
                            getView().showContentViews()
                            init(value)
                        } else {
                            getView().goneLoadView()
                            getView().showLoadViewEmpty()
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener { getIpvOffersProducts(sessionCode, ipvOffer) })
                    }
                })
        )
    }

    private var mInitDataSet : MutableList<IpvOfferProduct> = arrayListOf()

    private var mDataSet : MutableList<IpvOfferProduct> = arrayListOf()

    private fun init(items: List<IpvOfferProduct>) {
        mInitDataSet.clear()
        mInitDataSet.addAll(items)
        addItems(items)
    }

    private fun addItems(items: List<IpvOfferProduct>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        getView().addItems(items)
    }

    private var mEnableFilterItems = false

    override fun filterItems(text: String) {
        if (!mEnableFilterItems) {
            mEnableFilterItems = true
            return
        }
        val textNormalize = StringUtils.normalizeSpace(text)
        if (textNormalize.isEmpty()) {
            clearFilter()
            return
        }
        val filterDataSet = filterItems(textNormalize, mInitDataSet)
        if (filterDataSet.isEmpty()) {
            getView().showLoadViewEmpty(text, "")
            return
        }
        addItems(filterDataSet)
        getView().showContentViews()
        getView().goneLoadView()
    }

    private fun filterItems(text: String, dataSet: List<IpvOfferProduct>): List<IpvOfferProduct> {
        return dataSet.filter { StringUtils.containsIgnoreCase(it.getTextFilter(), text) }
    }

    private fun clearFilter() {
        addItems(mInitDataSet)
        getView().showContentViews()
        getView().goneLoadView()
        mEnableFilterItems = false
        getView().clearEditText()
    }
}