package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout

class ButtonsBottom : RelativeLayout {

    constructor(context: Context) : super(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private lateinit var btnLeft : Button
    private lateinit var btnRight : Button

    private fun init(context: Context, attrs: AttributeSet?) {
        var textLeft = ""
        var textRight = ""
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonsBottom, 0, 0
            )
            try {
                textLeft = typedArray.getString(R.styleable.ButtonsBottom_btnBottomTextLeft) ?: ""
                textRight = typedArray.getString(R.styleable.ButtonsBottom_btnBottomTextRight) ?: ""
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.buttons_content_bottom_left_right, this)
        gravity = Gravity.BOTTOM

        btnLeft = findViewById(R.id.btnLeft)
        btnLeft.setOnClickListener {
            mListener?.onClickLeft()
        }

        btnRight = findViewById(R.id.btnRight)
        btnRight.setOnClickListener {
            mListener?.onClickRight()
        }

        setTextButton(Direction.LEFT, textLeft)
        setTextButton(Direction.RIGHT, textRight)
    }

    enum class Direction {
        LEFT, RIGHT
    }

    fun setTextButton(direction: Direction, text: String) {
        when (direction) {
            Direction.LEFT -> btnLeft.text = text
            Direction.RIGHT -> btnRight.text = text
        }
    }

    fun setVisibilityButton(direction: Direction, visible : Boolean) {
        val visibility = if (visible) View.VISIBLE else View.GONE

        when (direction) {
            Direction.LEFT -> btnLeft.visibility = visibility
            Direction.RIGHT -> btnRight.visibility = visibility
        }
    }

    interface Listener {

        fun onClickLeft()

        fun onClickRight()
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        this.mListener = listener
    }
}