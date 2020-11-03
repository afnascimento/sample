package com.unilever.julia.components

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.unilever.julia.utils.bind

class ButtonBooleanCheck : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private val btnLeft by bind<TextView>(R.id.btnLeft)
    private val btnRight by bind<TextView>(R.id.btnRight)

    private var mSelect = Type.LEFT

    private var mListener: Listener? = null

    enum class Type {
        LEFT, RIGHT
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        var textLeft = ""
        var textRight = ""
        var checked = Type.LEFT.ordinal
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonBooleanCheck, 0, 0
            )
            try {
                textLeft = typedArray.getString(R.styleable.ButtonBooleanCheck_btnBoolean_textLeft) ?: ""
                textRight = typedArray.getString(R.styleable.ButtonBooleanCheck_btnBoolean_textRight) ?: ""
                checked = typedArray.getInt(R.styleable.ButtonBooleanCheck_btnBoolean_textChecked, checked)
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.boolean_button_layout, this)

        btnLeft.setOnClickListener {
            setSelected(Type.LEFT)
            if (mListener != null) {
                mListener!!.onChangeBooleanButton(mSelect)
            }
        }

        btnRight.setOnClickListener {
            setSelected(Type.RIGHT)
            if (mListener != null) {
                mListener!!.onChangeBooleanButton(mSelect)
            }
        }

        setTextButtons(textLeft, textRight)
        setSelected(Type.values()[checked])
    }

    fun setTextButtons(textLeft: String, textRight: String) {
        this.btnLeft.text = textLeft
        this.btnRight.text = textRight
    }

    @Suppress("DEPRECATION")
    private fun setStyleButton(button: TextView, resStyle: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            button.setTextAppearance(resStyle)
        } else {
            button.setTextAppearance(context, resStyle)
        }
    }

    fun setSelected(select: Type) {
        this.mSelect = select
        if (mSelect == Type.LEFT) {
            btnLeft.background = ContextCompat.getDrawable(context, R.drawable.bg_boolean_enabled)
            setStyleButton(btnLeft, R.style.BooleanButton_Enabled)

            btnRight.background = ContextCompat.getDrawable(context, R.drawable.bg_boolean_disabled)
            setStyleButton(btnRight, R.style.BooleanButton_Disabled)
        } else {
            btnLeft.background = ContextCompat.getDrawable(context, R.drawable.bg_boolean_disabled)
            setStyleButton(btnLeft, R.style.BooleanButton_Disabled)

            btnRight.background = ContextCompat.getDrawable(context, R.drawable.bg_boolean_enabled)
            setStyleButton(btnRight, R.style.BooleanButton_Enabled)
        }
    }

    interface Listener {
        fun onChangeBooleanButton(checked: Type)
    }

    fun setListener(mListener: Listener) {
        this.mListener = mListener
    }

    fun getChecked(): Type {
        return mSelect
    }
}
