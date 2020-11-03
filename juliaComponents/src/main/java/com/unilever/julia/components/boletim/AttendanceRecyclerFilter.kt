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
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.components.LoadView
import com.unilever.julia.components.R
import com.unilever.julia.components.enums.IconEnums
import org.apache.commons.lang3.StringUtils

class AttendanceRecyclerFilter : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var editText : EditText

    private var recyclerView : RecyclerView

    private var loadView : LoadView

    private val mAdapter : Adapter
    private val mFilter : Filter

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.content_filter_view, this)
        editText = findViewById(R.id.editText)
        recyclerView = findViewById(R.id.recyclerView)
        loadView = findViewById(R.id.filterLoadView)
        mAdapter = Adapter(recyclerView)
        mFilter = Filter(context, editText, recyclerView, loadView, mAdapter)
    }

    fun setHint(hint: String) {
        editText.hint = hint
    }

    fun addItems(selected: AttendanceModel, items : List<AttendanceModel>) {
        internalAddItems(selected, items)
    }

    fun addItems(items : List<AttendanceModel>) {
        internalAddItems(null, items)
    }

    private fun internalAddItems(selected: AttendanceModel?, items : List<AttendanceModel>) {
        if (items.isNullOrEmpty()) {
            showEmptyMessage()
        } else {
            if (selected != null) {
                mAdapter.setSelected(selected)
            }
            mFilter.init(items)
            recyclerView.setHasFixedSize(true)
            recyclerView.setItemViewCacheSize(20)
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = mAdapter
        }
    }

    fun updateSelected(selected : AttendanceModel) {
        mAdapter.unchecked(selected)
    }

    fun addListener(listener : Listener) {
        mAdapter.setListener(listener)
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

        private var mInitDataSet : MutableList<AttendanceModel> = arrayListOf()

        private var mDataSet : MutableList<AttendanceModel> = arrayListOf()

        fun init(items: List<AttendanceModel>) {
            mEditText.addTextChangedListener(this)
            mInitDataSet.clear()
            mInitDataSet.addAll(items)
            addItems(items)
        }

        private fun addItems(items: List<AttendanceModel>) {
            mDataSet.clear()
            mDataSet.addAll(items)
            mAdapter.addItems(items)
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

        private fun filterItems(text: String, dataSet: List<AttendanceModel>): List<AttendanceModel> {
            return dataSet.filter { StringUtils.containsIgnoreCase(it.textFilter, text) }
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

    interface Listener {
        fun onChangeAttendance(attendance: AttendanceModel)
    }

    class Adapter(val mRecyclerView: RecyclerView) : RecyclerView.Adapter<Adapter.ViewHolder>() {

        private var mSelected: AttendanceModel? = null

        private var mDataSet: MutableList<AttendanceModel> = arrayListOf()

        fun setSelected(selected: AttendanceModel) {
            mSelected = selected
        }

        fun unchecked(selected: AttendanceModel) {
            if (selected != mSelected) {
                updateSelected(selected, null)
            }
        }

        private var mListener : Listener? = null

        fun setListener(listener : Listener) {
            mListener = listener
        }

        fun addItems(items: List<AttendanceModel>) {
            mDataSet.clear()
            mDataSet.addAll(items)
            notifyDataSetChanged()
        }

        private fun updateSelected(item: AttendanceModel, listener: Listener?) {
            if (!mRecyclerView.isComputingLayout) {
                val oldPositionSelected = getPositionSelected(mSelected)
                mSelected = item
                listener?.onChangeAttendance(item)
                if (oldPositionSelected >= 0) {
                    notifyItemChanged(oldPositionSelected)
                }
            }
        }

        private fun getPositionSelected(item: AttendanceModel?): Int {
            if (item != null && mDataSet.contains(item)) {
                return mDataSet.indexOf(item)
            }
            return -1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.attendance_recycler_filter_item, parent, false))
        }

        override fun getItemCount(): Int {
            return mDataSet.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mDataSet[position]
            holder.textView.text = item.description

            val equals = mSelected?.equals(item) ?: false
            holder.radio.isChecked = equals
            holder.radio.setOnCheckedChangeListener { _, isChecked ->
                if (!equals && isChecked) {
                    updateSelected(item, mListener)
                }
            }
            holder.contentClick.setOnClickListener {
                holder.radio.isChecked = true
            }
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val contentClick = itemView.findViewById<View>(R.id.contentClick)
            val radio = itemView.findViewById<AppCompatRadioButton>(R.id.radio)
            val textView = itemView.findViewById<TextView>(R.id.textView)
        }
    }
}