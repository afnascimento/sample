package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.data.model.ButtonDisambiguationModel

class JuliaButtonsDisambiguation : RelativeLayout, IButtonClickListener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var julAnswerCard : JuliaAnswerComponent

    private var userChat : JuliaUserChat

    private var btClose : View

    private var contentButtonItems : LinearLayout

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_buttons_disambiguation, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        userChat = findViewById(R.id.userChat)

        btClose = findViewById(R.id.btClose)
        btClose.setOnClickListener {
            mListener?.onCloseButtonClick(mCloseButton)
        }

        contentButtonItems = findViewById(R.id.contentButtonItems)
    }

    fun showButtons() {
        userChat.visibility = View.GONE

        btClose.visibility = View.VISIBLE
        contentButtonItems.visibility = View.VISIBLE
    }

    fun hideButtons() {
        userChat.visibility = View.VISIBLE

        btClose.visibility = View.GONE
        contentButtonItems.visibility = View.GONE
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun setTextUser(text: String) {
        userChat.setText(text)
    }

    private var mCloseButton = ButtonClickListenerModel()

    fun setCloseButton(closeButton: ButtonClickListenerModel) {
        mCloseButton = closeButton
    }

    fun addButtonItems(items : MutableList<ButtonDisambiguationModel.Item>) {
        contentButtonItems.removeAllViews()
        for (it in items) {
            val button = ButtonDisambiguation(context)
            button.setItem(it)
            button.setListener(this)
            contentButtonItems.addView(button)
        }
    }

    private var mListener : Listener? = null

    interface Listener {
        fun onCloseButtonClick(closeButton: ButtonClickListenerModel)

        fun onIntentButtonClick(intentButton: ButtonClickListenerModel, closeButton: ButtonClickListenerModel)
    }

    fun setListener(listener : Listener) {
        mListener = listener
    }

    override fun onButtonClickListener(model: ButtonClickListenerModel) {
        mListener?.onIntentButtonClick(model, mCloseButton)
    }
}