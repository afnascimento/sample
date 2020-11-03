package com.unilever.julia.ui.component.autoComplete

import android.content.Context
import android.util.AttributeSet
import com.unilever.julia.R
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.ui.component.autoComplete.adapter.AutoCompleteAdapter
import com.unilever.julia.ui.component.autoComplete.adapter.TerritoryAdapter

class AutoCompleteTerritory : AutoCompleteComponent<Territory> {

    constructor(context: Context): super(context)

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun getAdapter(): AutoCompleteAdapter<Territory> {
        return TerritoryAdapter(context, R.layout.autocomplete_item_territory_layout)
    }

    override fun addItems(items: MutableList<Territory>) {
        mAdapter.addItems(items)
    }

    override fun filterItems(text: String) {
        mAdapter.filterItems(text)
    }

    override fun setOnItemClickListener(position: Int) {
        mListener?.onTerritorySelected(mAdapter.getItem(position), mIntent)
    }

    override fun onCloseAutoComplete(model: ButtonClickListenerModel) {
        mListener?.onCloseAutoComplete(model)
    }

    override fun onTextSendInput(text: String) {
        mListener?.onTextSendTerritory(text, mIntent)
    }

    interface Listener {

        fun onCloseAutoComplete(button : ButtonClickListenerModel)

        fun onTerritorySelected(territory : Territory, intent: String)

        fun onTextSendTerritory(text: String, intent: String)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }
}