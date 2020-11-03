package com.unilever.julia.ui.ipv.categoriesPriorities

import com.unilever.julia.R
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvCategory
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.ui.base.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class CategoriesPrioritiesPresenterImpl<V : CategoriesPrioritiesView, I : CategoriesPrioritiesInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor),
    CategoriesPrioritiesPresenter<V, I> {

    override fun onClick(ipvClient: IpvClient, ipvCategory: IpvCategory) {
        getView().redirectGroupUnitaryActivity(ipvClient, ipvCategory.context!!, ipvCategory.name ?: "")
    }

    override fun init(sessionCode: String, client: IpvClient) {
        getView().addDisposable(
            getInteractor().getIpvCategoriesPriorities(sessionCode, client.context!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<List<IpvCategory>>() {
                    override fun onNext(value: List<IpvCategory>) {
                        getView().setHeaderButtonColor(client.colorCode ?: "")
                        getView().setHeaderCommodity(client.getCommodityText())
                        getView().setHeaderCode(client.customerCode ?: "")
                        getView().setHeaderName(client.customerName ?: "")
                        getView().setHeaderDescription(getView().getTextString(R.string.ipv_score, client.getScoreText()))
                        getView().addItems(value)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener { init(sessionCode, client) })
                    }
                })
        )
    }
}