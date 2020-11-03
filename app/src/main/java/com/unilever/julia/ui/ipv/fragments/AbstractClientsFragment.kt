package com.unilever.julia.ui.ipv.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonTextRound
import com.unilever.julia.components.LoadView
import com.unilever.julia.data.model.ipv.IpvItem
import com.unilever.julia.data.model.ipv.IpvClient
import com.unilever.julia.ui.ipv.IpvActivity
import com.unilever.julia.ui.ipv.IpvInteractor
import com.unilever.julia.ui.ipv.IpvPresenter
import com.unilever.julia.ui.ipv.IpvView
import com.unilever.julia.ui.ipv.adapter.ClientesAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils

abstract class AbstractClientsFragment : Fragment(), ClientesAdapter.Listener {

    protected lateinit var mView: IpvView

    protected lateinit var mPresenter: IpvPresenter<IpvView, IpvInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mView = (context as IpvActivity)
        mPresenter = (context as IpvActivity).mPresenter
    }

    private lateinit var loadView : LoadView

    private lateinit var buttonRound : ButtonTextRound

    private lateinit var editText : EditText

    private lateinit var recyclerView : RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.ipv_clients, container, false)
    }

    private var mAdapter : ClientesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonRound = view.findViewById(R.id.buttonRound)
        buttonRound.visibility = View.GONE
        editText = view.findViewById(R.id.editText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterItems(s.toString())
            }
        })
        recyclerView = view.findViewById(R.id.recyclerView)
        loadView = view.findViewById(R.id.loadView)

        mAdapter = ClientesAdapter(this)
        mAdapter?.setTextViewScoreVisible(isScoreVisble())
        mAdapter?.setButtonRightVisible(isButtonRightVisible())
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = mAdapter

        if (isButtonRoundVisible()) {
            initButtonRound(getIpvModel())
        } else {
            buttonRound.visibility = View.GONE
        }

        init(getViews())
    }

    abstract fun isScoreVisble(): Boolean

    abstract fun isButtonRightVisible(): Boolean

    abstract fun isButtonRoundVisible(): Boolean

    private fun getViews(): List<View> {
        return if (isButtonRoundVisible()) {
            listOf(buttonRound, editText, recyclerView)
        } else {
            listOf(editText, recyclerView)
        }
    }

    abstract fun getIpvClients(): Observable<List<IpvClient>>

    abstract fun getIpvModel(): IpvItem?

    private fun initButtonRound(ipvModel: IpvItem?) {
        buttonRound.setColorBackground(ipvModel?.colorCode ?: "")
        buttonRound.setPercent(ipvModel?.percentage?.toFloat() ?: 0f)
    }

    private fun init(contentViews: List<View>) {
        mView.addDisposable(
            getIpvClients()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe {
                    mView.showLoadView(contentViews, loadView)
                }
                .subscribeWith(object : DisposableObserver<List<IpvClient>>() {
                    override fun onNext(value: List<IpvClient>) {
                        addInitialItems(value)
                        addItems(value)
                    }

                    override fun onComplete() {
                        mView.hideLoadView(contentViews, loadView)
                    }

                    override fun onError(e: Throwable) {
                        mView.onErrorHandler(contentViews, loadView, e, LoadView.OnClickListener { init(contentViews) })
                    }
                })
        )
    }

    private var mInitDataSet : MutableList<IpvClient> = arrayListOf()

    private var mDataSet : MutableList<IpvClient> = arrayListOf()

    fun addInitialItems(items: List<IpvClient>) {
        mInitDataSet.clear()
        mInitDataSet.addAll(items)
    }

    fun addItems(items: List<IpvClient>) {
        mDataSet.clear()
        mDataSet.addAll(items)
        mAdapter?.addItems(mDataSet)
    }

    fun showFilterViews() {
        val views = getViews()
        for (view in views) {
            view.visibility = View.VISIBLE
        }
    }

    fun showLoadView() {
        loadView.visibility = View.VISIBLE
    }

    fun hideLoadView() {
        loadView.visibility = View.GONE
    }

    fun setTextFilterInput(text: String) {
        editText.setText(text, TextView.BufferType.EDITABLE)
    }

    private var mEnableFilterItems = false

    private fun clearFilter() {
        addItems(mInitDataSet)
        showFilterViews()
        hideLoadView()
        mEnableFilterItems = false
        setTextFilterInput("")
    }

    fun filterItems(text: String, dataSet: List<IpvClient>): List<IpvClient> {
        return dataSet.filter { StringUtils.containsIgnoreCase(it.getTextFilter(), text) }
    }

    fun filterItems(text: String) {
        if (!mEnableFilterItems) {
            mEnableFilterItems = true
            return
        }
        val textNormalize = StringUtils.normalizeSpace(text)
        if (textNormalize.isEmpty()) {
            clearFilter()
            return
        }
        val isMinCharacters = StringUtils.length(textNormalize) >= 2
        if (isMinCharacters) {
            val filterDataSet = filterItems(textNormalize, mInitDataSet)
            if (filterDataSet.isEmpty()) {
                mView.showLoadViewEmpty(
                    listOf(recyclerView), loadView,
                    getString(R.string.julia_load_empty_filter_title_with_word, text),
                    getString(R.string.julia_load_empty_filter_text)
                )
                return
            }

            addItems(filterDataSet)
            showFilterViews()
            hideLoadView()
        }
    }
}