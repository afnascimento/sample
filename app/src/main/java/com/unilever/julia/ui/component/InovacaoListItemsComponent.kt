package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import org.apache.commons.lang3.StringUtils

class InovacaoListItemsComponent : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var contentDetalheItems : LinearLayout

    var tvTitle : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.inovacao_detalhe_content_items, this)

        tvTitle = findViewById(R.id.tvTitle)

        contentDetalheItems = findViewById(R.id.contentDetalheItems)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setItems(items: MutableList<String>) {
        contentDetalheItems.removeAllViewsInLayout()
        for (text in items) {
            val view = LayoutInflater.from(context).inflate(R.layout.inovacao_detalhe_content_item, null, false)
            val tvItemText = view.findViewById<TextView>(R.id.tvItemText)
            tvItemText.text = StringUtils.trimToEmpty(text)
            contentDetalheItems.addView(view)
        }
    }
}