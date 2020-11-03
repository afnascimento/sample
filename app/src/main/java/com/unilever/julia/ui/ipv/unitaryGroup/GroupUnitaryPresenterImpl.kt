package com.unilever.julia.ui.ipv.unitaryGroup

import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.goods.Good
import com.unilever.julia.data.model.goods.IpvGoodsGroup
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.data.model.ipv.IpvContext
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class GroupUnitaryPresenterImpl<V : GroupUnitaryView, I : GroupUnitaryInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), GroupUnitaryPresenter<V, I> {

    override fun onClick(ipvClient: IpvClient, ipvGoodsGroup: IpvGoodsGroup, description: String) {
        getView().redirectIpvProductsActivity(ipvClient, ipvGoodsGroup.context ?: IpvContext(), description)
    }

    override fun init(sessionCode: String, ipvClient: IpvClient, ipvContext: IpvContext, description: String) {
        getView().addDisposable(
            getInteractor().getIpvGoods(sessionCode, ipvContext)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<Good>() {
                    override fun onNext(value: Good) {
                        getView().setHeaderButtonColor(ipvClient.colorCode ?: "")
                        getView().setHeaderCommodity(ipvClient.getCommodityText())
                        getView().setHeaderCode(ipvClient.customerCode ?: "")
                        getView().setHeaderName(ipvClient.customerName ?: "")
                        getView().setHeaderDescription(description)
                        getView().addGoods(value)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            init(sessionCode, ipvClient, ipvContext, description)
                        })
                    }
                })
        )
    }
}