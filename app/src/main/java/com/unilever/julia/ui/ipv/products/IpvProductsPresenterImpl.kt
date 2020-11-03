package com.unilever.julia.ui.ipv.products

import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.data.model.ipv.IpvProduct
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class IpvProductsPresenterImpl<V : IpvProductsView, I : IpvProductsInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), IpvProductsPresenter<V, I> {

    override fun init(sessionCode: String, client: IpvClient, context: IpvContext, headerDescription: String) {
        getView().addDisposable(
            getInteractor().getIpvProducts(sessionCode, context)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<List<IpvProduct>>() {
                    override fun onNext(value: List<IpvProduct>) {
                        getView().setHeaderButtonColor(client.colorCode ?: "")
                        getView().setHeaderCommodity(client.getCommodityText())
                        getView().setHeaderCode(client.customerCode ?: "")
                        getView().setHeaderName(client.customerName ?: "")
                        getView().setHeaderDescription(headerDescription)
                        getView().addItems(value)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener { init(sessionCode, client, context, headerDescription) })
                    }
                })
        )
    }
}