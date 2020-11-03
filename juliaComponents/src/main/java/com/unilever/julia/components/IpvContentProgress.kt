package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.utils.Utils

class IpvContentProgress : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var progressHorizontal : ProgressHorizontal

    private var textViewIcon : JuliaTextViewIcon

    private var tvLabel : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_content_progress, this)
        this.progressHorizontal = findViewById(R.id.progressHorizontal)
        this.textViewIcon = findViewById(R.id.textViewIcon)
        this.tvLabel = findViewById(R.id.tvLabel)
    }

    fun setProgress(text: String, score: Double, color: String, isVisibleText: Boolean) {
        progressHorizontal.init(score, color, isVisibleText)
        tvLabel.text = Utils.getTextFromHtml(text)
    }
}