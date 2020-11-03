package com.unilever.julia.components

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.components.model.IpvRecylerItem
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.setAbreviateText
import org.apache.commons.lang3.StringUtils
import java.lang.StringBuilder

class IpvRecylerFilter : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var editText : EditText

    private var recyclerView : RecyclerView

    private var loadView : LoadView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_recycler_filter, this)
        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerView)
        loadView = findViewById(R.id.filterLoadView)
    }

    fun setHint(hint: String) {
        editText.hint = hint
    }

    fun <T : IpvRecylerItem> addItems(items : List<T>, listener: Adapter.Listener<T>) {
        if (items.isNullOrEmpty()) {
            showEmptyMessage()
        } else {
            val adapter = Adapter(listener)
            val filter = Filter(context, editText, recyclerView, loadView, adapter)
            filter.init(items)
            recyclerView.setHasFixedSize(true)
            recyclerView.setItemViewCacheSize(20)
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
        }
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

    class Filter<T : IpvRecylerItem>(val mContext: Context,
                                     val mEditText: EditText,
                                     val mRecyclerView: RecyclerView,
                                     val mLoadView: LoadView,
                                     val mAdapter : Adapter<T>): TextWatcher {

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            filterItems(s.toString())
        }

        private var mInitDataSet : MutableList<T> = arrayListOf()

        private var mDataSet : MutableList<T> = arrayListOf()

        fun init(items: List<T>) {
            mEditText.addTextChangedListener(this)
            mInitDataSet.clear()
            mInitDataSet.addAll(items)
            addItems(items)
        }

        private fun addItems(items: List<T>) {
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

        private fun filterItems(text: String, dataSet: List<T>): List<T> {
            return dataSet.filter { StringUtils.containsIgnoreCase(it.getTextFilter(), text) }
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

    class Adapter<T : IpvRecylerItem>(val mListener : Listener<T>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

        interface Listener<T> {
            fun onClick(item: T)
        }

        private var mDataSet: MutableList<T> = arrayListOf()

        fun addItems(items: List<T>) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ipv_recycler_filter_item, parent, false))
        }

        override fun getItemCount(): Int {
            return mDataSet.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mDataSet[position]

            holder.buttonRound.setIcon(item.getIconCode())
            holder.buttonRound.setColorBackground(item.getIconColor())

            val textCopyPaste = StringBuilder()

            if (item.isTextRegularVisible()) {
                val text = item.getTextRegular()
                textCopyPaste.append(text).append("\n")
                holder.textViewRegular.text = text
                //holder.textViewRegular.setAbreviateText(1)
                holder.textViewRegular.visibility = View.VISIBLE
            } else {
                holder.textViewRegular.visibility = View.GONE
            }

            if (item.isTextBoldVisible()) {
                val text = item.getTextBold()
                textCopyPaste.append(text)
                holder.textViewBold.text = text
                //holder.textViewBold.setAbreviateText(1)
                holder.textViewBold.visibility = View.VISIBLE
            } else {
                holder.textViewBold.visibility = View.GONE
            }


            val onLongClickListener = OnLongClickListener {
                val context = it.context
                Utils.copyPasteContext(context,
                    StringUtils.trim(textCopyPaste.toString()),
                    context.getString(R.string.text_copy_paste))
                return@OnLongClickListener true
            }
            if (item.isButtonClickVisible()) {
                val onClickListener = OnClickListener { mListener.onClick(item) }
                holder.contentClick.setOnClickListener(onClickListener)
                holder.contentClick.setOnLongClickListener(onLongClickListener)
                holder.buttonRight.setOnClickListener(onClickListener)
                holder.buttonRight.visibility = View.VISIBLE
            } else {
                holder.contentClick.setOnLongClickListener(onLongClickListener)
                holder.buttonRight.visibility = View.GONE
            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val buttonRound = itemView.findViewById<ButtonIconRound>(R.id.buttonRound)
            val textViewRegular = itemView.findViewById<TextView>(R.id.textViewRegular)
            val textViewBold = itemView.findViewById<TextView>(R.id.textViewBold)
            val contentClick = itemView.findViewById<View>(R.id.contentClick)
            val buttonRight = itemView.findViewById<View>(R.id.buttonRight)
        }
    }
}