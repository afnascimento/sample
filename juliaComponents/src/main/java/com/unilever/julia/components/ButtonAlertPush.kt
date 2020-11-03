package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView

class ButtonAlertPush : RelativeLayout  {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val tvText: TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var textSize = 0f
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonAlertPush, 0, 0
            )
            try {
                textSize = typedArray.getDimension(R.styleable.ButtonAlertPush_btAlertPushTextSize, textSize)
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.button_alert_push, this)
        tvText = findViewById(R.id.tvText)

        if (textSize != 0f) {
            tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
        }
    }

    fun setNumPushs(num: Int) {
        if (num <= 0) {
            tvText.visibility = View.GONE
        } else {
            tvText.visibility = View.VISIBLE
            tvText.text = "+$num"
        }
    }
}