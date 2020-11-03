package com.unilever.julia.components.boletim

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.components.R
import com.unilever.julia.components.model.BoletimCard

class BoletimCardChat : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var juliaAnswer : JuliaAnswerComponent
    private var boletimCard : BoletimCardProgress

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.boletim_card_chat, this)
        juliaAnswer = findViewById(R.id.julAnswerCard)
        boletimCard = findViewById(R.id.boletimCardProgress)
    }

    fun setText(text: String) {
        juliaAnswer.setText(text)
    }

    fun initBind(card : BoletimCard?) {
        if (card == null) {
            boletimCard.visibility = View.GONE
        } else {
            boletimCard.visibility = View.VISIBLE
            boletimCard.initBind(card)
        }
    }

    fun updateBind(card : BoletimCard, attendanceFilter: AttendanceFilter, filters: BoletimFiltros) {
        boletimCard.visibility = View.VISIBLE
        if (filters.isEmpty()) {
            boletimCard.reset()
        } else {
            boletimCard.updateBind(card, attendanceFilter, filters)
        }
    }

    fun setListener(listener : BoletimCardProgress.Listener) {
        boletimCard.setListener(listener)
    }
}