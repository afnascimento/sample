package com.unilever.julia.ui.status.detalhe

import com.google.gson.Gson
import com.unilever.julia.R
import com.unilever.julia.data.enums.StatusCardEnum
import com.unilever.julia.data.enums.StatusEnum
import com.unilever.julia.data.model.*
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class StatusDetalhePresenterImpl<V : StatusDetalheView, I : StatusDetalheInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), StatusDetalhePresenter<V, I> {

    var mGson: Gson = Gson()

    var mDataSet : MutableList<IStatusPedido> = arrayListOf()

    var mDataSetItems : MutableList<StatusPedidoItem> = arrayListOf()

    override fun filterItems(text: String) {
        if (text.length >= 3 && mDataSetItems.isNotEmpty()) {
            val filter = mDataSetItems.filter { StringUtils.containsIgnoreCase(it.content.produto, text) }.toMutableList()

            if (filter.isEmpty()) {
                getView().showLoadViewEmpty(
                    getView().getContext().getString(R.string.julia_load_empty_filter_title),
                    getView().getContext().getString(R.string.julia_load_empty_filter_text),
                    getView().getContext().getString(R.string.julia_load_empty_filter_clear),
                    LoadView.OnClickListener {
                        getView().clearTextEditText()
                        getView().hideLoadView()
                        getView().setItems(mDataSet)
                    }
                )
            } else {
                getView().setItems(filter.toMutableList())
            }
        } else {
            getView().setItems(mDataSet)
        }
    }

    override fun sendStatusPedido(sessionCode: String, model: StatusPedidoModel.Item) {
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

                        val list = mGson.fromJson(
                            conversations.getAnswer().technicalText ?: "", Array<StatusPedidoResponse>::class.java
                        )

                        mDataSetItems.clear()

                        var count = 0

                        // mapeamento
                        for (it in list) {

                            val status = StatusPedidoItem()

                            if (count == 0) {
                                status.statusCardEnum = StatusCardEnum.ITEM_TOP
                            } else if (count == (list.size - 1)) {
                                status.statusCardEnum = StatusCardEnum.ITEM_BOTTOM
                            } else {
                                status.statusCardEnum = StatusCardEnum.ITEM_MIDDLE
                            }

                            // itens transporte
                            if (!it.items.isNullOrEmpty()) {
                                for (transp in it.items!!) {
                                    status.addTransport(transp.codigo ?: "", transp.data ?: "", transp.quantidade ?: "")
                                }
                            }

                            val statusPedido : String = it.statusPedido ?: ""

                            if (StatusPedidoItem.isType(statusPedido, StatusPedidoItem.Type.PDTM)) {
                                status.addValue(StatusPedidoItem.Field.PRODUTO, it, true)
                                status.addValue(StatusPedidoItem.Field.COD_SKU, it, true)
                                status.addValue(StatusPedidoItem.Field.DATA_CRIACAO, it, true)
                                status.addValue(StatusPedidoItem.Field.DATA_REQUERIDA, it, true)
                                status.addValue(StatusPedidoItem.Field.VALOR, it, true)
                                status.addValue(StatusPedidoItem.Field.QUANTIDADE, it, true)
                            } else if (StatusPedidoItem.isType(statusPedido, StatusPedidoItem.Type.PDIS)) {
                                status.addValue(StatusPedidoItem.Field.PRODUTO, it, true)
                                status.addValue(StatusPedidoItem.Field.COD_SKU, it, true)
                                status.addValue(StatusPedidoItem.Field.DATA_AGENDA, it, true)
                                status.addValue(StatusPedidoItem.Field.VALOR, it, true)
                                status.addValue(StatusPedidoItem.Field.QUANTIDADE, it, true)
                            } else {
                                status.addValue(StatusPedidoItem.Field.PRODUTO, it, true)
                                status.addValue(StatusPedidoItem.Field.COD_SKU, it, true)

                                val statusEnum = StatusEnum.getStatusEnum(statusPedido)
                                if (statusEnum == StatusEnum.ANULADO) {
                                    status.addValue(StatusPedidoItem.Field.MOTIVO, it, false)
                                }

                                if (statusEnum == StatusEnum.FATURADO) {
                                    //status.addValue(StatusPedidoItem.Field.DATA_AGENDA, it, false)
                                }

                                status.addValue(StatusPedidoItem.Field.VALOR, it, true)
                                status.addValue(StatusPedidoItem.Field.QUANTIDADE, it, true)
                            }

                            mDataSetItems.add(status)

                            count++
                        }

                        mDataSet.clear()
                        mDataSet.add(model)
                        mDataSet.addAll(mDataSetItems)

                        getView().setItems(mDataSet)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            sendStatusPedido(sessionCode, model)
                        })
                    }
                })
        )
    }
}