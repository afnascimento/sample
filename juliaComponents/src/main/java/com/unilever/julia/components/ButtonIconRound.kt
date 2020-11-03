package com.unilever.julia.components

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.utils.Utils

class ButtonIconRound : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvIcon : JuliaTextViewIcon

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var iconEnum = IconEnums.CALENDARIO

        var iconSize = 0f

        var bgColor = 0

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonIconRound, 0, 0
            )
            try {
                iconEnum = IconEnums.getIconEnumByStyleable(typedArray,
                    R.styleable.ButtonIconRound_julTextViewIcon
                )

                iconSize = typedArray.getDimension(R.styleable.ButtonIconRound_btIcRoundTextSize, iconSize)

                bgColor = typedArray.getColor(R.styleable.ButtonIconRound_btIcRoundBgColor, bgColor)
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.button_horizontal_icon, this)

        tvIcon = findViewById(R.id.tvIcon)
        setIcon(iconEnum)
        if (iconSize != 0f) {
            setTextSize(iconSize)
        }
        if (bgColor != 0) {
            setColorBackground(bgColor)
        }
    }

    fun setIcon(iconEnum : IconEnums) {
        tvIcon.setIcon(iconEnum.iconHexa)
    }

    fun setIcon(iconHexa : String) {
        tvIcon.setIcon(iconHexa)
    }

    fun setColorBackground(color: String) {
        val contentMain = findViewById<View>(R.id.contentMain)
        Utils.setColorDrawable(color, contentMain.background)
    }

    fun setColorBackground(color: Int) {
        val contentMain = findViewById<View>(R.id.contentMain)
        Utils.setColorDrawable(color, contentMain.background)
    }

    private fun setTextSize(size : Float) {
        tvIcon.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
    }
}