package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.TextView
import com.unilever.julia.utils.Utils

class ButtonWrapContent : RelativeLayout {

    enum class ButtonColor(var colorRes: Int) {
        RED(R.color.colorRed),
        GREEN(R.color.colorGreen),
        ORANGE(R.color.colorOrange),
        GRAY(R.color.colorGray),
        BLUE(R.color.colorBlue)
    }

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvTextButton : TextView

    private var btnLink : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var textAttrs = context.getString(R.string.button_wrap_text)
        var colorAttrs = ButtonColor.GREEN

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonWrapContent, 0, 0
            )
            try {
                textAttrs = typedArray.getString(R.styleable.ButtonWrapContent_btnWrapText) ?: textAttrs

                val btnWrapColor = typedArray.getInt(R.styleable.ButtonWrapContent_btnWrapColor, -1)
                if (btnWrapColor >= 0) {
                    colorAttrs = ButtonColor.values()[btnWrapColor]
                }
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.button_wrapcontent, this)

        btnLink = findViewById(R.id.btnLink)

        tvTextButton = findViewById(R.id.tvTextButton)

        setText(textAttrs)

        setColor(colorAttrs)
    }

    fun setText(text: String) {
        tvTextButton.text = text
    }

    fun setColor(color: Int) {
        Utils.setColorDrawable(color, btnLink.background)
    }

    fun setColor(colorHexa: String) {
        Utils.setColorDrawable(colorHexa, btnLink.background)
    }

    fun setColor(btColor: ButtonColor) {
        Utils.setColorDrawable(ContextCompat.getColor(context, btColor.colorRes), btnLink.background)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        btnLink.setOnClickListener(l)
    }
}