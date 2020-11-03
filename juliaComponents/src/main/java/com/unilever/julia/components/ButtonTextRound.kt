package com.unilever.julia.components

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ButtonTextRound : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var viewBackground : View

    private var tvText : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        var textValue = context.getString(R.string._99porcent)

        var textPercent = -1f

        var textSize = context.resources.getDimension(R.dimen.button_text_label_size)

        var bgColor = ContextCompat.getColor(context, R.color.colorGreen)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonTextRound, 0, 0
            )
            try {
                textValue = StringUtils.defaultIfBlank(typedArray.getString(R.styleable.ButtonTextRound_btTextValue), textValue) ?: ""

                textPercent = typedArray.getFloat(R.styleable.ButtonTextRound_btTextPercent, textPercent)

                textSize = typedArray.getDimension(R.styleable.ButtonTextRound_btTextSize, textSize)

                bgColor = typedArray.getColor(R.styleable.ButtonTextRound_btTextBgColor, bgColor)
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.button_text_round, this)

        tvText = findViewById(R.id.tvText)

        viewBackground = findViewById(R.id.background)

        val isTextPercent = (textPercent != -1f)

        if (isTextPercent) {
            setPercent(textPercent)
        } else {
            setText(textValue)
        }

        setTextSize(textSize)

        setColorBackground(bgColor)
    }

    fun setText(text: String) {
        tvText.text = text
    }

    fun setPercent(percent: Float) {
        tvText.text = Utils.getTextPercent(percent.toDouble())
    }

    fun setColorBackground(color: String) {
        Utils.setColorDrawable(color, viewBackground.background)
    }

    fun setColorBackground(color: Int) {
        Utils.setColorDrawable(color, viewBackground.background)
    }

    private fun setTextSize(size : Float) {
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }
}