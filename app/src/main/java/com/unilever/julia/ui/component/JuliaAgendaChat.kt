package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.data.model.AgendaModel

class JuliaAgendaChat : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var contentItems : LinearLayout

    var juliaAnswer : JuliaAnswerComponent

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_agenda_chat, this)

        contentItems = findViewById(R.id.contentItems)

        juliaAnswer = findViewById(R.id.juliaAgendaAnswer)
    }

    fun setText(text : String) {
        juliaAnswer.setText(text)
    }

    fun addItems(items : MutableList<AgendaModel.Item>, listener: JuliaAgendaCard.Listener) {
        contentItems.removeAllViewsInLayout()
        for (it in items) {
            val card = JuliaAgendaCard(context)
            card.addItem(it)
            card.setListener(listener)
            contentItems.addView(card)
        }
    }
}