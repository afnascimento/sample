package com.unilever.julia.ui.component.autoComplete

import android.content.Context
import android.util.AttributeSet
import com.unilever.julia.R
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.ui.component.autoComplete.adapter.AutoCompleteAdapter
import com.unilever.julia.ui.component.autoComplete.adapter.CustomerAdapter

class AutoCompleteCustomer : AutoCompleteComponent<Customer> {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getAdapter(): AutoCompleteAdapter<Customer> {
        return CustomerAdapter(context, R.layout.autocomplete_item_cliente_layout)
    }

    override fun addItems(items: MutableList<Customer>) {
        mAdapter.addItems(items)
    }

    override fun filterItems(text: String) {
        mAdapter.filterItems(text)
    }

    override fun setOnItemClickListener(position: Int) {
        mListener?.onCustomerSelected(mAdapter.getItem(position), mIntent)
    }

    override fun onCloseAutoComplete(model: ButtonClickListenerModel) {
        mListener?.onCloseAutoComplete(model)
    }

    override fun onTextSendInput(text: String) {
        mListener?.onTextSendCustomer(text, mIntent)
    }

    interface Listener {

        fun onCloseAutoComplete(button : ButtonClickListenerModel)

        fun onCustomerSelected(customer : Customer, intent: String)

        fun onTextSendCustomer(text: String, intent: String)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }
}