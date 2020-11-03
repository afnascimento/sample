package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout

class JuliaInnovationChatVertical : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var julAnswerCard : JuliaAnswerComponent

    private var contentCardItems : LinearLayout

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_innovation_chat_vertical, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentCardItems = findViewById(R.id.contentCardItems)
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun <T : ButtonInnovationVertical.Item> setItems(items : MutableList<T>, listener: ButtonInnovationVertical.Listener<T>) {
        contentCardItems.removeAllViewsInLayout()
        for (it in items) {
            val card = ButtonInnovationVertical<T>(context)
            card.setItem(it)
            card.setListener(listener)
            contentCardItems.addView(card)
        }
    }
}