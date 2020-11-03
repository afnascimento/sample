package com.unilever.julia.components

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.unilever.julia.utils.Utils

class ButtonIconBadge : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var contentClick : View

    private var contentBadge : LinearLayout

    private var tvText : TextView
    private var icon : ImageView
    private var badge : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val drawableIcon : Drawable?
        val text : String
        val textColor : Int
        val enabledColor : Int
        val disabledColor : Int
        val visibleIcon : Boolean
        val visibleBadge : Boolean
        val enabled : Boolean

        val typedArray : TypedArray? = Utils.Styleable.getTypedArray(context, attrs, R.styleable.ButtonIconBadge)
        try {
            drawableIcon = Utils.Styleable.getDrawable(context,
                typedArray,
                R.styleable.ButtonIconBadge_btnIcBdIcon,
                R.drawable.ic_icone_filtro)

            text = Utils.Styleable.getString(typedArray,
                R.styleable.ButtonIconBadge_btnIcBdText, context.getString(R.string.filtros))

            textColor = Utils.Styleable.getColor(context, typedArray,
                R.styleable.ButtonIconBadge_btnIcBdTextColor, R.color.colorWhite)

            enabledColor = Utils.Styleable.getColor(context, typedArray,
                R.styleable.ButtonIconBadge_btnIcBdEnabledColor, R.color.colorBlue)

            disabledColor = Utils.Styleable.getColor(context, typedArray,
                R.styleable.ButtonIconBadge_btnIcBdDisabledColor, R.color.colorDisabled)

            visibleIcon = Utils.Styleable.getBoolean(typedArray,
                R.styleable.ButtonIconBadge_btnIcBdIconVisible, true)

            visibleBadge = Utils.Styleable.getBoolean(typedArray,
                R.styleable.ButtonIconBadge_btnIcBdBadgeVisible, true)

            enabled = Utils.Styleable.getBoolean(typedArray,
                R.styleable.ButtonIconBadge_btnIcBdEnabled, true)
        } finally {
            typedArray?.recycle()
        }
        inflate(context, R.layout.button_icon_badge, this)

        contentClick = findViewById(R.id.contentClick)

        contentBadge = findViewById(R.id.contentBadge)
        contentBadge.post {
            setMaxWidthTextView(contentBadge.height, contentBadge.width)
        }
        icon = contentBadge.findViewById(R.id.icon)
        badge = contentBadge.findViewById(R.id.badge)
        tvText = contentBadge.findViewById(R.id.tvText)

        setIcon(drawableIcon)
        setText(text)
        setTexColor(textColor)
        setColorEnabled(enabledColor)
        setColorDisabled(disabledColor)
        setVisibleIcon(visibleIcon)
        setVisibleBagde(visibleBadge)
        updateEnabled(enabled)
    }

    fun setIcon(drawable: Drawable?) {
        icon.setImageDrawable(drawable)
    }

    fun setTexColor(@ColorInt colorRes : Int) {
        icon.setColorFilter(colorRes)
        tvText.setTextColor(colorRes)
    }

    fun setText(text: String) {
        tvText.text = text
    }

    fun setVisibleBagde(visible: Boolean) {
        badge.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setVisibleIcon(visible: Boolean) {
        icon.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun setMaxWidthTextView(height : Int, width: Int) {
        val widthIcon = if (icon.visibility == View.VISIBLE) icon.width else 0
        val widthBadge = if (badge.visibility == View.VISIBLE) badge.width else 0
        val maxWidth = width - widthIcon - widthBadge
        tvText.maxWidth = maxWidth
        //tvText.requestLayout()
        //icon.requestLayout()
        //badge.requestLayout()
        contentBadge.requestLayout()
    }

    private var mEnabled = true

    override fun setEnabled(enabled: Boolean) {
        updateEnabled(enabled)
        super.setEnabled(mEnabled)
    }

    private fun updateEnabled(enabled: Boolean) {
        mEnabled = enabled
        if (mEnabled) {
            setBgColorEnabled()
        } else {
            setBgColorDisabled()
        }
    }

    private fun setBgColorDisabled() {
        if (mDrawableDisabled == null) {
            alpha = 0.4f
        } else {
            contentClick.background = mDrawableDisabled
        }
    }

    private fun setBgColorEnabled() {
        if (mDrawableEnabled == null) {
            alpha = 1.0f
        } else {
            contentClick.background = mDrawableEnabled
        }
    }

    private var mDrawableEnabled : Drawable? = null

    fun setColorEnabled(@ColorInt colorRes : Int) {
        val background = ContextCompat.getDrawable(context, R.drawable.bg_button_icon_badge)
        val drawable = Utils.setColorDrawable(colorRes, background)
        mDrawableEnabled = Utils.getBackgroundRippleDrawable(context, R.color.colorWhite, drawable)
    }

    private var mDrawableDisabled : Drawable? = null

    fun setColorDisabled(@ColorInt colorRes : Int) {
        val background = ContextCompat.getDrawable(context, R.drawable.bg_button_icon_badge)
        val drawable = Utils.setColorDrawable(colorRes, background)
        mDrawableDisabled = drawable
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        contentClick.setOnClickListener{
            if (mEnabled) {
                listener?.onClick(this)
            }
        }
    }
}