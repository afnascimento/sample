package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView

class ButtonInnovationVertical<T : ButtonInnovationVertical.Item> : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.button_innovation_vertical, this)

        findViewById<ButtonWrapContent>(R.id.btnDetail).setOnClickListener {
            if (mItem != null) {
                mListener?.onButtonDetailClick(mItem!!)
            }
        }
    }

    interface Item {
        fun icon(): String
        fun icon2(): String
        fun color(): String
        fun text(): String
        fun projects(): Int
    }

    private var mItem : T? = null

    fun setItem(it : T) {
        mItem = it

        findViewById<JuliaTextViewIcon>(R.id.tvIcon1).setIcon(it.icon(), it.color())

        findViewById<JuliaTextViewIcon>(R.id.tvIcon2).setIcon(it.icon2(), it.color())

        findViewById<TextView>(R.id.tvDescription).text = it.text()

        findViewById<TextView>(R.id.tvProjectNum).text = it.projects().toString()
    }

    interface Listener<T> {
        fun onButtonDetailClick(item: T)
    }

    private var mListener : Listener<T>? = null

    fun setListener(listener : Listener<T>) {
        mListener = listener
    }
}