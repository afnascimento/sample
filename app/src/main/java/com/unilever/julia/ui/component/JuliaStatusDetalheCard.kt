package com.unilever.julia.ui.component

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import com.github.aakira.expandablelayout.ExpandableRelativeLayout
import com.unilever.julia.R
import com.unilever.julia.data.enums.StatusEnum
import com.unilever.julia.data.model.StatusPedidoItem
import org.apache.commons.lang3.StringUtils

class JuliaStatusDetalheCard : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_status_detalhe_card_item, this)

        contentDetalheCardItems = findViewById(R.id.contentDetalheCardItems)
    }

    var contentDetalheCardItems : LinearLayout

    fun setItem(status: StatusPedidoItem, tipoStatus: StatusEnum) {
        contentDetalheCardItems.removeAllViewsInLayout()

        for (it in status.values) {
            val view = LayoutInflater.from(context).inflate(R.layout.julia_status_detalhe_item_v2, contentDetalheCardItems, false)

            view.findViewById<TextView>(R.id.tvTitle).text = StringUtils.trimToEmpty(it.key)

            view.findViewById<TextView>(R.id.tvValue).text = StringUtils.trimToEmpty(it.value)

            contentDetalheCardItems.addView(view)
        }

        if (status.transports.isNotEmpty()) {
            contentDetalheCardItems.addView(getViewFixedTransports(status, tipoStatus))
        }
    }

    fun getViewFixedTransports(status: StatusPedidoItem, tipoStatus: StatusEnum) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.julia_status_detalhe_item_transport, contentDetalheCardItems, false)

        if (tipoStatus == StatusEnum.PENDENTE) {
            // titles table header
            view.findViewById<TextView>(R.id.tvTitleCol1).text = context.getString(R.string.num_transport)
            view.findViewById<TextView>(R.id.tvTitleCol2).text = context.getString(R.string.data_saida)
            view.findViewById<TextView>(R.id.tvTitleCol3).text = context.getString(R.string.caixas_title)
        } else {
            // titles table header
            view.findViewById<TextView>(R.id.tvTitleCol1).text = context.getString(R.string.num_fatura)
            view.findViewById<TextView>(R.id.tvTitleCol2).text = context.getString(R.string.faturado_em)
            view.findViewById<TextView>(R.id.tvTitleCol3).text = context.getString(R.string.caixas_title)
        }

        val contentItems : LinearLayout = view.findViewById(R.id.contentItems)

        for (it in status.transports) {
            contentItems.addView(getJuliaStatusDetalheItemTransport(it, contentItems))
        }

        return view
    }

    fun getViewAccordion(status: StatusPedidoItem, tipoStatus: StatusEnum): View {
        // transport
        val view = LayoutInflater.from(context).inflate(R.layout.julia_status_detalhe_item_accordion, contentDetalheCardItems, false)

        val expandableLayout = view.findViewById<ExpandableRelativeLayout>(R.id.expandableLayout)

        if (tipoStatus == StatusEnum.PENDENTE) {
            view.findViewById<TextView>(R.id.tvTitle).text = context.getString(R.string.transporte)
            view.findViewById<TextView>(R.id.tvValue).text = context.getString(R.string.existem_transportes)

            // titles table header
            expandableLayout.findViewById<TextView>(R.id.tvTitleCol1).text = context.getString(R.string.num_transport)
            expandableLayout.findViewById<TextView>(R.id.tvTitleCol2).text = context.getString(R.string.data_saida)
            expandableLayout.findViewById<TextView>(R.id.tvTitleCol3).text = context.getString(R.string.caixas_title)
        } else {
            view.findViewById<TextView>(R.id.tvTitle).text = context.getString(R.string.detail)
            view.findViewById<TextView>(R.id.tvValue).text = context.getString(R.string.clique_para_ver_detalhes)

            // titles table header
            expandableLayout.findViewById<TextView>(R.id.tvTitleCol1).text = context.getString(R.string.num_fatura)
            expandableLayout.findViewById<TextView>(R.id.tvTitleCol2).text = context.getString(R.string.faturado_em)
            expandableLayout.findViewById<TextView>(R.id.tvTitleCol3).text = context.getString(R.string.caixas_title)
        }

        val contentArrow = view.findViewById<View>(R.id.contentArrow)
        val btnArrow = contentArrow.findViewById<AppCompatImageView>(R.id.btnArrow)

        contentArrow.setOnClickListener {
            expandableLayout.toggle(300, FastOutLinearInInterpolator())
        }

        expandableLayout.setListener(object : ExpandableLayoutListenerAdapter() {

            override fun onOpened() {
                btnArrow.setImageResource(R.drawable.ic_arrow_up)
            }

            override fun onClosed() {
                btnArrow.setImageResource(R.drawable.ic_arrow_down)
            }
        })
        expandableLayout.expand()//expandableLayout.collapse()

        val contentItems = expandableLayout.findViewById<LinearLayout>(R.id.contentItems)

        for (it in status.transports) {
            contentItems.addView(getJuliaStatusDetalheItemTransport(it, contentItems))
        }

        return view
    }

    fun getJuliaStatusDetalheItemTransport(it: StatusPedidoItem.Transport, parent: ViewGroup) : View {
        val view = LayoutInflater.from(context).inflate(R.layout.julia_status_detalhe_item_transport_item, parent, false)
        view.findViewById<TextView>(R.id.tvCode).text = it.code
        view.findViewById<TextView>(R.id.tvDate).text = it.date
        view.findViewById<TextView>(R.id.tvQuantity).text = it.quantity
        return view
    }
}