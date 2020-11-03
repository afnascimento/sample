package com.unilever.julia.ui.clientes

import com.unilever.julia.R
import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.ui.base.*
import com.unilever.julia.components.LoadView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils

import javax.inject.Inject

class ClientesPresenterImpl<V : ClientesView, I : ClientesInteractor>
@Inject constructor(mView: V, mInteractor: I) : BasePresenterImpl<V, I>(mView, mInteractor), ClientesPresenter<V, I> {

    private var mInitDataSet : MutableList<ClienteModel> = arrayListOf()

    private var mDataSet : MutableList<ClienteModel> = arrayListOf()

    private fun addItems(items: MutableList<ClienteModel>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        getView().addItems(mDataSet)
    }

    override fun setTitleToolbar(territory: String) {
        val split = StringUtils.split(StringUtils.trimToEmpty(territory), ";")
        if (split.size == 1) {
            getView().setTitle(getView().getContext().getString(R.string.meu_territorio_title, split[0]))
        } else {
            getView().setTitle(getView().getContext().getString(R.string.meus_territorios_title))
        }
    }

    override fun getClientes(sessionCode: String, intention: String, territory: String) {
        getView().addDisposable(
            getInteractor().getClientes(sessionCode, intention, territory)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    getView().showLoadView()
                }
                .subscribeWith(object : DisposableObserver<MutableList<ClienteModel>>() {
                    override fun onNext(value: MutableList<ClienteModel>) {
                        mInitDataSet = sortItems(false, value)
                        addItems(mInitDataSet)
                    }

                    override fun onComplete() {
                        getView().hideLoadView()
                    }

                    override fun onError(e: Throwable) {
                        getView().onErrorHandler(e, LoadView.OnClickListener {
                            getClientes(sessionCode, intention, territory)
                        })
                    }
                })
        )
    }

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
            getView().hideRecyclerViewItems()
            getView().showLoadViewEmpty(
                getView().getContext().getString(R.string.julia_load_empty_filter_title),
                getView().getContext().getString(R.string.julia_load_empty_filter_text),
                getView().getContext().getString(R.string.julia_load_empty_filter_clear),
                LoadView.OnClickListener { clearFilter() }
            )
            getView().showContentLoadView()
            return
        }
        getView().showRecyclerViewItems()
        addItems(filterDataSet)
        getView().hideContentLoadView()
    }

    private var mEnableFilterItems = true

    override fun clearFilter() {
        addItems(mInitDataSet)
        getView().showRecyclerViewItems()
        getView().hideContentLoadView()
        mEnableFilterItems = false
        getView().setTextFilterInput("")
    }

    override fun sortItems(asc: Boolean) {
        mInitDataSet = sortItems(asc, mInitDataSet)
        addItems(sortItems(asc, mDataSet))
    }

    private fun selectorGetValue(p: ClienteModel): Double = p.getPercentSort()

    override fun sortItems(asc: Boolean, dataSet: MutableList<ClienteModel>): MutableList<ClienteModel> {
        if (asc) {
            // menor para maior
            return dataSet.sortedBy { selectorGetValue(it) }.toMutableList()
        } else {
            // maior para menor
            return dataSet.sortedByDescending { selectorGetValue(it) }.toMutableList()
        }
    }

    override fun filterItems(text: String, dataSet: MutableList<ClienteModel>): MutableList<ClienteModel> {
        return dataSet.filter { StringUtils.containsIgnoreCase(it.customer?.getTextFilter(), text) }.toMutableList()
    }
}