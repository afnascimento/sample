package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.data.model.StatusPedidoModel

class JuliaStatusChat : RelativeLayout, JuliaStatusCard.CardClickListener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var julAnswerCard : JuliaAnswerComponent

    private var contentCardItems : LinearLayout

    private var feedbackButtonsLike : FeedbackButtonsLike

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_status_chat, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentCardItems = findViewById(R.id.contentCardItems)

        feedbackButtonsLike = findViewById(R.id.feedbackButtonsLike)
    }

    fun refreshButtons() {
        feedbackButtonsLike.onRefreshButtons()
    }

    fun setFeedbackButtonsLikeListener(listener : FeedbackButtonsLike.Listener) {
        feedbackButtonsLike.setListener(listener)
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun addItems(items : MutableList<StatusPedidoModel.Item>, listener: JuliaStatusCard.Listener) {
        contentCardItems.removeAllViewsInLayout()
        for (it in items) {
            val card = JuliaStatusCard(context)
            card.setItem(it)
            card.setAbreviateTextClient()
            card.setListener(listener)
            card.setCardClickListener(this@JuliaStatusChat)
            contentCardItems.addView(card)
        }
    }

    override fun onClickListener(card : JuliaStatusCard) {
        if (card.isCardSelected()) {
            mListener?.addItemShare(card.getItem())
        } else {
            mListener?.removeItemShare(card.getItem())
        }

        var countJuliaStatusCard = 0
        var countNotSelected = 0

        for (i in 0..contentCardItems.childCount) {
            val statusCard = contentCardItems.getChildAt(i)
            if (statusCard is JuliaStatusCard) {
                countJuliaStatusCard++
                if (!statusCard.isCardSelected()) {
                    countNotSelected++
                }
            }
        }

        if (countJuliaStatusCard == countNotSelected) {
            for (i in 0..contentCardItems.childCount) {
                val statusCard = contentCardItems.getChildAt(i)
                if (statusCard is JuliaStatusCard) {
                    statusCard.setLongClick(true)
                }
            }
            mListener?.hideContentShare()
        }
    }

    fun clearAllSelectedCards() {
        for (i in 0..contentCardItems.childCount) {
            val statusCard = contentCardItems.getChildAt(i)
            if (statusCard is JuliaStatusCard) {
                statusCard.setCardSelected(false)
                statusCard.setLongClick(true)
            }
        }
    }

    override fun onLongClickListener(card : JuliaStatusCard) {
        mListener?.visibleContentShare()
        mListener?.addItemShare(card.getItem())
        for (i in 0..contentCardItems.childCount) {
            val statusCard = contentCardItems.getChildAt(i)
            if (statusCard is JuliaStatusCard) {
                statusCard.setLongClick(false)
            }
        }
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun addItemShare(item : StatusPedidoModel.Item)
        fun removeItemShare(item : StatusPedidoModel.Item)
        fun visibleContentShare()
        fun hideContentShare()
        fun setCardComponent(component: JuliaStatusChat)
    }
}