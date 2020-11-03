package com.unilever.julia.ui.pedidos

import com.unilever.julia.R
import com.unilever.julia.data.enums.FiltroOrdenacaoEnum
import com.unilever.julia.data.model.FiltroModel
import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.data.model.PedidosClienteModel
import com.unilever.julia.ui.base.BasePresenterImpl
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class PedidosClientePresenterImpl<V : PedidosClienteView, I : PedidosClienteInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), PedidosClientePresenter<V, I> {

    private var mDataSet : MutableList<PedidoClienteResponse> = arrayListOf()

    override fun getPedidosCliente(model : PedidosClienteModel) {
        getView().addDisposable(
            getInteractor().getPedidosCliente(model)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<MutableList<PedidoClienteResponse>>() {
                    override fun onNext(pedidos: MutableList<PedidoClienteResponse>) {
                        mDataSet = pedidos
                        getView().addItems(pedidos)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().hideLoadView()
                        getView().onErrorHandler(e, LoadView.OnClickListener { getPedidosCliente(model) })
                    }
                })
        )
    }

    fun isValueInPeriod(valor : Double?, valores: Pair<Double, Double>?): Boolean {
        if (valor != null && valores != null) {
            if (valor >= valores.first && valor <= valores.second) {
                return true
            }
        }
        return false
    }

    private var mFiltroModel : FiltroModel = FiltroModel()

    override fun filtrarDados(filtro : FiltroModel) {
        filtrarDados(filtro, mDataSet)
    }

    override fun filtrarDados(filtro : FiltroModel, dataSet : MutableList<PedidoClienteResponse>) {
        mFiltroModel = filtro
        if (mFiltroModel.isClearFilter()) {
            getView().addItems(dataSet)
        } else {
            var listFilter : MutableList<PedidoClienteResponse> = arrayListOf()
            listFilter.addAll(dataSet)

            if (mFiltroModel.periodo != null) {
                listFilter = listFilter.filter { Utils.dateInPeriod(it.getDate(), mFiltroModel.periodo) }.toMutableList()
            }
            if (mFiltroModel.valores != null) {
                listFilter = listFilter.filter { isValueInPeriod(it.getValue(), mFiltroModel.valores) }.toMutableList()
            }
            if (mFiltroModel.ordenacao != null) {
                if (mFiltroModel.ordenacao == FiltroOrdenacaoEnum.DATA_MENOS_RECENTE) { // menor para maior
                    val filter = listFilter.filter { it.getDate() != null }
                    val sortedBy = filter.sortedBy { selectorGetDate(it) }
                    listFilter = sortedBy.toMutableList()
                } else if (mFiltroModel.ordenacao == FiltroOrdenacaoEnum.DATA_MAIS_RECENTE) { // maior para menor
                    val filter = listFilter.filter { it.getDate() != null }
                    val sortedBy = filter.sortedByDescending { selectorGetDate(it) }
                    listFilter = sortedBy.toMutableList()
                } else if (mFiltroModel.ordenacao == FiltroOrdenacaoEnum.MENOR_VALOR) {
                    listFilter.sortBy { selectorGetValue(it) }
                } else if (mFiltroModel.ordenacao == FiltroOrdenacaoEnum.MAIOR_VALOR) {
                    listFilter.sortByDescending { selectorGetValue(it) }
                }
            }
            getView().addItems(listFilter)
            if (listFilter.isEmpty()) {
                getView().showLoadViewEmpty(
                    getView().getContext().getString(R.string.julia_load_empty_filter_title),
                    getView().getContext().getString(R.string.julia_load_empty_filter_text),
                    getView().getContext().getString(R.string.julia_load_empty_filter_clear),
                    LoadView.OnClickListener { limparFiltros() }
                )
            }
        }
    }

    private fun limparFiltros() {
        getView().hideLoadView()
        getView().addItems(mDataSet)
        mFiltroModel.clear()
    }

    private fun selectorGetDate(p: PedidoClienteResponse): Date? = p.getDate()

    private fun selectorGetValue(p: PedidoClienteResponse): Double = p.getValue()

    override fun redirectFiltroPedido() {
        getView().redirectFiltroPedidoActivity(mFiltroModel)
    }
}