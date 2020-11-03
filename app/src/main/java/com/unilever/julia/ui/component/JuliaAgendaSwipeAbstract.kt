package com.unilever.julia.ui.component

import android.view.View
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.model.AgendaModel
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.setAbreviateText
import org.apache.commons.lang3.StringUtils

class JuliaAgendaSwipeAbstract(view: View) {

    private var tvDescricao : TextView = view.findViewById(R.id.tvDescricao)

    private var tvLocal : TextView = view.findViewById(R.id.tvLocal)

    private var tvAno : TextView = view.findViewById(R.id.tvAno)

    private var tvDia : TextView = view.findViewById(R.id.tvDia)

    private var tvMes : TextView = view.findViewById(R.id.tvMes)

    private var cardDetalhe : View = view.findViewById(R.id.contentSwipeCard)

    init {
        tvDescricao.setAbreviateText(1)
        cardDetalhe.setOnClickListener { mListener?.onDetalheEvento(mItem) }
    }

    private var mItem : AgendaModel.Item = AgendaModel.Item()

    fun getItem() : AgendaModel.Item {
        return mItem
    }

    fun addItem(item : AgendaModel.Item) {
        mItem = item

        tvDescricao.text = StringUtils.trimToEmpty(item.description)
        tvDescricao.setAbreviateText(1)

        tvLocal.text = StringUtils.trimToEmpty(item.subject)
        tvLocal.setAbreviateText(1)

        if (item.dataVisible) {
            val data = item.getData()
            tvAno.text = Utils.getYearInDate(data).toString()
            tvDia.text = StringUtils.leftPad(Utils.getDayInMonth(data).toString(), 2, "0")
            tvMes.text = Utils.getMonthAbreviate(data)
        }

        setVisibleData(item.dataVisible)
    }

    fun setVisibleData(visible: Boolean) {
        if (visible) {
            tvAno.visibility = View.VISIBLE
            tvDia.visibility = View.VISIBLE
            tvMes.visibility = View.VISIBLE
        } else {
            tvAno.visibility = View.GONE
            tvDia.visibility = View.GONE
            tvMes.visibility = View.GONE
        }
    }

    private var mListener : IJuliaAgendaSwipeCardListener? = null

    fun setListener(listener : IJuliaAgendaSwipeCardListener) {
        mListener = listener
    }
}