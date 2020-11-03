package com.unilever.julia.ui.status.pendentes

import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.SendStatusParcel
import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class StatusPendentesPresenterImpl<V : StatusPendentesView, I : StatusPendentesInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), StatusPendentesPresenter<V, I> {

    var mGson: Gson = Gson()

    override fun getPendentes(sessionCode: String, model: StatusPedidoModel.Item) {

        val parcel = SendStatusParcel(
            sessionCode,
            model.code ?: "",
            model.order?.idCustomer ?: "",
            model.order?.codPedido ?: "",
            model.order?.statusPedido ?: "",
            ""
        )

        getView().addDisposable(
            getInteractor().sendStatusPedido(parcel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<Conversations>() {
                    override fun onNext(conversations: Conversations) {
                        var items : MutableList<StatusPedidoModel.Item> = arrayListOf()
                        try {
                            val arrayItems = mGson.fromJson(
                                StringUtils.trimToEmpty(conversations.getAnswer().technicalText), Array<StatusPedidoModel.Item>::class.java
                            )
                            items = arrayItems.toMutableList()
                        } catch (e : Throwable) {
                            e.printStackTrace()
                        }

                        getView().addJuliaStatusCardItems(items)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            getPendentes(sessionCode, model)
                        })
                    }
                })
        )
    }
}