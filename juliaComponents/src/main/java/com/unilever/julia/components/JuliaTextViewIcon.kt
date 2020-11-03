package com.unilever.julia.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.utils.JavaUtils
import com.unilever.julia.utils.Utils

class JuliaTextViewIcon : AppCompatTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var iconEnum = IconEnums.FOODS_2

        if (attrs != null) {
            val typedArray : TypedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.JuliaTextViewIcon, 0, 0
            )
            try {
                iconEnum = IconEnums.getIconEnumByStyleable(typedArray,
                    R.styleable.JuliaTextViewIcon_julTextViewIcon
                )
            } finally {
                typedArray.recycle()
            }
        }

        val font = Typeface.createFromAsset(context.assets, "icomoon.ttf")
        typeface = font
        text = getTextIcon(iconEnum.iconHexa)
    }

    fun setIcon(icon : IconEnums) {
        text = getTextIcon(icon.iconHexa)
    }

    fun setIcon(iconHexa : String) {
        text = getTextIcon(iconHexa)
    }

    fun setIcon(iconHexa : String, colorHexa : String) {
        setIcon(iconHexa)

        val colorIcon = Utils.getColorByHexa(colorHexa)
        if (colorIcon != 0) {
            setTextColor(colorIcon)
        }
    }

    private fun getTextIcon(valHexStr : String): String {
        return try {
            JavaUtils.parseStringHexa(valHexStr)
        } catch (e : Throwable) {
            ""
        }
    }
}