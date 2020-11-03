package com.unilever.julia.ui.component.autoComplete.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.database.entity.Customer
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.setAbreviateText
import org.apache.commons.lang3.StringUtils

class CustomerAdapter(context: Context, resource: Int) : AutoCompleteAdapter<Customer>(context, resource) {

    private fun getFilterItems(text: String, items : MutableList<Customer>): MutableList<Customer> {
        var textNormalize = StringUtils.normalizeSpace(text)
        if (!(textNormalize.length >= 2)) {
            return arrayListOf()
        }
        textNormalize = StringUtils.upperCase(Utils.removeAccents(textNormalize))

        val filterDataSet = items.filter { StringUtils.contains(it.getTextFilter(), textNormalize) }.toMutableList()
        if (filterDataSet.isEmpty()) {
            return arrayListOf()
        }
        return filterDataSet
    }

    override fun filterItems(text: String) {
        filterItems(getFilterItems(text, mDefaultData))
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = mInflater.inflate(mResource, parent, false)

            holder = ViewHolder()
            holder.tvCustomerName = view.findViewById(R.id.tvCustomerName)
            holder.tvCustomerName.setAbreviateText(1)

            holder.tvCustomerAdress = view.findViewById(R.id.tvCustomerAdress)
            holder.tvCustomerAdress.setAbreviateText(1)

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = getItem(position)
        holder.tvCustomerName.text = item.getNameWithCode()
        holder.tvCustomerAdress.text = item.getAddress()

        return view
    }

    private class ViewHolder {
        lateinit var tvCustomerName: TextView
        lateinit var tvCustomerAdress: TextView
    }
}