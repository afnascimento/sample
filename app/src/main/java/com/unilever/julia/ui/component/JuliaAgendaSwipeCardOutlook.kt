package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.data.model.AgendaModel

class JuliaAgendaSwipeCardOutlook : RelativeLayout, IJuliaAgendaSwipeCard {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var mAgenda : JuliaAgendaSwipeAbstract

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val owner = defStyleAttr == 0
        if (owner) {
            inflate(context, R.layout.julia_agenda_swipe_card_outlook_owner, this)
        } else {
            inflate(context, R.layout.julia_agenda_swipe_card_outlook, this)
        }

        findViewById<View>(R.id.btnOutlook).visibility = View.VISIBLE
        findViewById<View>(R.id.tvIcon2).visibility = View.GONE
        findViewById<View>(R.id.tvLocal).visibility = View.GONE

        mAgenda = JuliaAgendaSwipeAbstract(this)

        if (owner) {
            findViewById<View>(R.id.strokeRightCard).visibility = View.VISIBLE
            findViewById<View>(R.id.btnExcluir).setOnClickListener { mListener?.onExcluirEvento(mAgenda.getItem()) }
        } else {
            findViewById<View>(R.id.strokeRightCard).visibility = View.GONE
        }
    }

    override fun addItem(item : AgendaModel.Item) {
        mAgenda.addItem(item)
    }

    override fun setVisibleData(visible: Boolean) {
        mAgenda.setVisibleData(visible)
    }

    private var mListener : IJuliaAgendaSwipeCardListener? = null

    override fun setListener(listener : IJuliaAgendaSwipeCardListener) {
        mListener = listener
        mAgenda.setListener(listener)
    }
}