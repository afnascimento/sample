package com.unilever.julia.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.utils.Utils

class JuliaAnswerComponent : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var contentMessage : View

    private var tvText : TextView

    private var contentTextClick : View

    private var tvTextClick : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var cardText = context.getString(R.string.julia_chat_msg)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.JuliaAnswerComponent, 0, 0
            )
            try {
                cardText = typedArray.getString(R.styleable.JuliaAnswerComponent_julAnsCompText) ?: cardText
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.julia_chat_answer, this)

        tvText = findViewById(R.id.tvJuliaText)

        contentTextClick = findViewById(R.id.contentTextClick)

        tvTextClick = findViewById(R.id.tvTextClick)

        contentMessage = findViewById(R.id.contentMessage)

        setText(cardText)
    }

    fun enableCopyText() {
        contentMessage.setOnLongClickListener {
            val cManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val cData = ClipData.newPlainText("text", tvText.text.toString())
            cManager.primaryClip = cData

            Utils.showToast(context, context.getString(R.string.text_copy_paste))

            return@setOnLongClickListener true
        }
    }

    fun setVisibleContentClick(visible: Boolean) {
        if (visible) {
            contentTextClick.visibility = View.VISIBLE
            contentTextClick.setOnClickListener {
                mListener?.onClick()
            }
        } else {
            contentTextClick.visibility = View.GONE
        }
    }

    fun setTextClick(text: String) {
        tvTextClick.text = text
    }

    fun setText(text : String) {
        try {
            tvText.text = Utils.getTextFromHtml(text)
        } catch (e : Throwable) {
            tvText.text = text
        }
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        this.mListener = listener
    }

    interface Listener {
        fun onClick()
    }
}