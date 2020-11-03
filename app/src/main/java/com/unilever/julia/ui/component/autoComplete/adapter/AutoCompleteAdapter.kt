package com.unilever.julia.ui.component.autoComplete.adapter

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.unilever.julia.ui.component.IAutoCompleteModel

abstract class AutoCompleteAdapter<T : IAutoCompleteModel> (context: Context, resource: Int) : ArrayAdapter<T>(context, resource) {

    protected var mInflater : LayoutInflater = LayoutInflater.from(context)

    protected var mResource : Int = resource

    protected var mDefaultData : MutableList<T> = arrayListOf()

    protected var mData : MutableList<T> = arrayListOf()

    fun addItems(items: MutableList<T>) {
        mDefaultData.clear()
        mDefaultData.addAll(items)
        filterItems(items)
    }

    abstract fun filterItems(text: String)

    fun filterItems(items: MutableList<T>) {
        mData.clear()
        mData.addAll(items)
        if (mData.isNotEmpty()) {
            notifyDataSetChanged()
        } else {
            notifyDataSetInvalidated()
        }
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun getItem(position: Int): T {
        return mData[position]
    }
}