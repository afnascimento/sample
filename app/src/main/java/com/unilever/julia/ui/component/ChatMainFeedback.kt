package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonsBottom

class ChatMainFeedback : RelativeLayout, ButtonsBottom.Listener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var editTextFeedback : EditText

    private var buttonsBottom : ButtonsBottom

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.feedback_card, this)

        editTextFeedback = findViewById(R.id.editTextFeedback)

        buttonsBottom = findViewById(R.id.buttonsBottom)
        buttonsBottom.setListener(this)
    }

    override fun onClickLeft() {
        mListener?.onCancelFeedback()
    }

    override fun onClickRight() {
        mListener?.onConfirmFeedback(editTextFeedback.text.toString(), mIntent)
    }

    fun show() {
        editTextFeedback.setText("", TextView.BufferType.EDITABLE)
        visibility = View.VISIBLE
    }

    fun hide() {
        visibility = View.GONE
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun onCancelFeedback()
        fun onConfirmFeedback(text: String, intent: String)
    }

    private var mIntent: String = ""

    fun setIntentFeedback(intent: String) {
        mIntent = intent
    }
}