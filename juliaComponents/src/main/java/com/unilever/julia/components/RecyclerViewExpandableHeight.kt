package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewExpandableHeight : RecyclerView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST))
        layoutParams.height = measuredHeight
    }
}
