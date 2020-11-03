package com.unilever.julia.ui.component.autoComplete.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.database.entity.Territory
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.setAbreviateText
import org.apache.commons.lang3.StringUtils

class TerritoryAdapter(context: Context, resource: Int) : AutoCompleteAdapter<Territory>(context, resource) {

    private fun getFilterItems(text: String, items : MutableList<Territory>): MutableList<Territory> {
        var textNormalize = StringUtils.normalizeSpace(text)
        if (!(textNormalize.length >= 2)) {
            return arrayListOf()
        }
        textNormalize = StringUtils.upperCase(Utils.removeAccents(textNormalize))

        val filterDataSet = items.filter { StringUtils.contains(it.code ?: "", textNormalize) }.toMutableList()
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
            holder.tvTerritoryName = view.findViewById(R.id.tvTerritoryName)
            holder.tvTerritoryName.setAbreviateText(1)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val item = getItem(position)
        holder.tvTerritoryName.text = item.code ?: ""

        return view
    }

    private class ViewHolder {
        lateinit var tvTerritoryName: TextView
    }
}