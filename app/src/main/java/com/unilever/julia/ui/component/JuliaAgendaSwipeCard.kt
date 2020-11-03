package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.data.model.AgendaModel

class JuliaAgendaSwipeCard : RelativeLayout, IJuliaAgendaSwipeCard {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    //private var swipeLayout : SwipeLayout

    private var btnCancelar : View

    private var btnConcluir : View

    private var mAgenda : JuliaAgendaSwipeAbstract

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_agenda_swipe_card, this)
        mAgenda = JuliaAgendaSwipeAbstract(this)

        btnCancelar = findViewById(R.id.btnCancelar)
        btnCancelar.setOnClickListener { mListener?.onCancelarEvento(mAgenda.getItem()) }

        btnConcluir = findViewById(R.id.btnConcluir)
        btnConcluir.setOnClickListener { mListener?.onConcluirEvento(mAgenda.getItem()) }

        /*
        swipeLayout = findViewById(R.id.swipe_layout)
        swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {

            override fun onOpen(direction: Int, isContinuous: Boolean) {
                Log.d("SWIPE_LAYOUT", "onOpen")
                //if (direction == SwipeLayout.LEFT && !isContinuous) {
                //}
            }

            override fun onClose() {
                Log.d("SWIPE_LAYOUT", "onClose")
            }
        })
        */
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