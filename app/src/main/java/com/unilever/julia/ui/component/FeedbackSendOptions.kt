package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.data.model.FeedbackOptionsModel

class FeedbackSendOptions : RelativeLayout, ButtonComponent.Listener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var julAnswerCard : JuliaAnswerComponent

    private var contentScroll : View

    private var contentButtonItems : LinearLayout

    private var userMessage : JuliaUserChat

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.feedback_send_options, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentScroll = findViewById(R.id.contentScroll)

        contentButtonItems = findViewById(R.id.contentButtonItems)

        userMessage = findViewById(R.id.userMessage)
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun addOptions(items : MutableList<FeedbackOptionsModel.Item>) {
        contentButtonItems.removeAllViews()
        for (it in items) {
            val button = ButtonComponent(context)
            button.setVisibilityIcon(false)
            button.setText(it.title)
            button.setIntencao(it.intencao)
            button.setListener(this@FeedbackSendOptions)
            contentButtonItems.addView(button)
        }
    }

    override fun onButtonClickListener(text: String, url: String, intencao: String) {
        contentScroll.visibility = View.GONE
        userMessage.setText(text)
        userMessage.visibility = View.VISIBLE
        mListener?.onFeedbackOptionClick(text, intencao)
    }

    private var mListener : Listener? = null

    fun setListener(listener: Listener) {
        mListener = listener
    }

    interface Listener {
        fun onFeedbackOptionClick(text: String, intencao: String)
    }
}