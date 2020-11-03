package com.unilever.julia.components.boletim

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.R
import com.unilever.julia.components.enums.IconEnums
import org.apache.commons.lang3.StringUtils

class BoletimRecyclerFilter : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var editText : EditText

    private var recyclerView : RecyclerView

    private var loadView : LoadView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        ConstraintLayout.inflate(context, R.layout.content_filter_view, this)
        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerView)
        loadView = findViewById(R.id.filterLoadView)
    }

    fun setHint(hint: String) {
        editText.hint = hint
    }

    private val mAdapter = Adapter()

    fun addItems(items : List<String>) {
        if (items.isNullOrEmpty()) {
            showEmptyMessage()
        } else {
            val filter = Filter(context, editText, recyclerView, loadView, mAdapter)
            filter.init(items)
            recyclerView.setHasFixedSize(true)
            recyclerView.setItemViewCacheSize(20)
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = mAdapter
        }
    }

    fun getItemsSelected(): List<String> {
        return mAdapter.getSelecteds()
    }

    private fun showEmptyMessage() {
        editText.visibility = View.GONE
        recyclerView.visibility = View.GONE
        loadView.showCustom(
            IconEnums.ICON_ERROR_FACE,
            context.getString(R.string.julia_load_empty_title),
            context.getString(R.string.julia_load_empty_text)
        )
    }

    class Filter(val mContext: Context,
                 val mEditText: EditText,
                 val mRecyclerView: RecyclerView,
                 val mLoadView: LoadView,
                 val mAdapter : Adapter): TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            filterItems(s.toString())
        }

        private var mInitDataSet : MutableList<String> = arrayListOf()

        private var mDataSet : MutableList<String> = arrayListOf()

        fun init(items: List<String>) {
            mEditText.addTextChangedListener(this)
            mInitDataSet.clear()
            mInitDataSet.addAll(items)
            addItems(items)
        }

        private fun addItems(items: List<String>) {
            mDataSet.clear()
            mDataSet.addAll(items)
            mAdapter.addItems(mDataSet)
        }

        private fun showContentViews() {
            mRecyclerView.visibility = View.VISIBLE
        }

        private fun hideContentViews() {
            mRecyclerView.visibility = View.GONE
        }

        private fun hideLoadView() {
            mLoadView.visibility = View.GONE
        }

        private fun clearEditText() {
            mEditText.setText("", TextView.BufferType.EDITABLE)
        }

        private fun clearFilter() {
            addItems(mInitDataSet)
            showContentViews()
            hideLoadView()
            mEnableFilterItems = false
            clearEditText()
        }

        private fun showLoadViewEmpty(text: String) {
            hideContentViews()
            mLoadView.showCustom(
                IconEnums.ICON_ERROR_FACE,
                mContext.getString(R.string.julia_load_empty_filter_title_with_word, text),
                mContext.getString(R.string.julia_load_empty_filter_text)
            )
        }

        private fun filterItems(text: String, dataSet: List<String>): List<String> {
            return dataSet.filter { StringUtils.containsIgnoreCase(it, text) }
        }

        private var mEnableFilterItems = false

        private fun filterItems(text: String) {
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
                showLoadViewEmpty(text)
                return
            }
            addItems(filterDataSet)
            showContentViews()
            hideLoadView()
        }
    }

    class Adapter : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private var mDataSelecteds: MutableList<String> = arrayListOf()

        private var mDataSet: MutableList<String> = arrayListOf()

        fun addItems(items: List<String>) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }

        fun getSelecteds(): List<String> {
            return mDataSelecteds
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.boletim_recycler_filter_item, parent, false))
        }

        override fun getItemCount(): Int {
            return mDataSet.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mDataSet[position]
            holder.textView.text = item
            holder.checkBox.isChecked = mDataSelecteds.contains(item)
            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (!mDataSelecteds.contains(item)) {
                        mDataSelecteds.add(item)
                    }
                } else {
                    mDataSelecteds.remove(item)
                }
            }
            holder.contentClick.setOnClickListener {
                val newChecked = !holder.checkBox.isChecked
                holder.checkBox.isChecked = newChecked
            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val contentClick = itemView.findViewById<View>(R.id.contentClick)
            val checkBox = itemView.findViewById<AppCompatCheckBox>(R.id.checkBox)
            val textView = itemView.findViewById<TextView>(R.id.textView)
        }
    }
}