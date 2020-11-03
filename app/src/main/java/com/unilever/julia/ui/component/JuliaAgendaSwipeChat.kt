package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.data.model.AgendaModel

class JuliaAgendaSwipeChat : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var contentItems : LinearLayout

    var juliaAnswer : JuliaAnswerComponent

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_agenda_swipe_chat, this)

        contentItems = findViewById(R.id.contentItems)

        juliaAnswer = findViewById(R.id.juliaAgendaAnswer)
    }

    fun setText(text : String) {
        juliaAnswer.setText(text)
    }

    fun addItems(items : MutableList<AgendaModel.Item>, listener : IJuliaAgendaSwipeCardListener) {
        contentItems.removeAllViewsInLayout()
        for (it in items) {
            if (it.isEventOutlook()) {
                val card = JuliaAgendaSwipeCardOutlook(context, null, if(it.owner) 0 else 1)
                card.addItem(it)
                card.setListener(listener)
                contentItems.addView(card)
            } else {
                val card = JuliaAgendaSwipeCard(context)
                card.addItem(it)
                card.setListener(listener)
                contentItems.addView(card)
            }
        }
    }
}