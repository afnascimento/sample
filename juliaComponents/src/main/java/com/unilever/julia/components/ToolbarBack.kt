package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class ToolbarBack : Toolbar {

    constructor(context: Context) : super(context, null) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var isInitCalled = false

    private lateinit var imgBackArrow : ImageView

    private lateinit var tvBackTitle : TextView

    private fun init(context: Context, attrs: AttributeSet?) {
        var attrsTitle = ""
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ToolbarBack, 0, 0
            )
            try {
                attrsTitle = typedArray.getString(R.styleable.ToolbarBack_toolbarBackTitle) ?: ""
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.toolbar_back_layout, this)

        isInitCalled = true

        imgBackArrow = findViewById(R.id.imgArrowIcon)
        tvBackTitle = findViewById(R.id.tvTitle)

        title = attrsTitle
    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle("")
        tvBackTitle.text = title?.toString() ?: ""
    }

    fun setClickListenerBackIcon(listener: OnClickListener) {
        imgBackArrow.setOnClickListener(listener)
    }
}