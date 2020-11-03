package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.unilever.julia.R

class ChatButtonsEditText : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var btnMicrophone : View

    private var btnSendMessage : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.chat_main_bottom_buttons, this)

        btnMicrophone = findViewById(R.id.btnMicrophone)
        btnMicrophone.setOnClickListener {
            mListener?.onMicrophoneClick()
        }

        btnSendMessage = findViewById(R.id.btnSendMessage)
        btnSendMessage.setOnClickListener {
            mListener?.onSendMessageClick()
        }

        setVisibilityButtons(Type.MICROPHONE)
    }

    enum class Type {
        MICROPHONE, SEND_MESSAGE
    }

    fun setVisibilityButtons(type: Type) {
        if (type == Type.MICROPHONE) {
            btnMicrophone.visibility = View.VISIBLE
            btnSendMessage.visibility = View.GONE
        } else {
            btnMicrophone.visibility = View.GONE
            btnSendMessage.visibility = View.VISIBLE
        }
    }

    private var mListener : OnClickListener? = null

    fun setOnClickListener(listener: OnClickListener) {
        mListener = listener
    }

    interface OnClickListener {

        fun onMicrophoneClick()

        fun onSendMessageClick()
    }
}