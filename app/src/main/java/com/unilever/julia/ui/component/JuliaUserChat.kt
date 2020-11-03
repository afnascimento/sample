package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.utils.Utils

class JuliaUserChat : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var contentClick : View

    private var textView : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_user_chat, this)

        contentClick = findViewById(R.id.contentClick)

        textView = findViewById(R.id.tvTextUser)

        contentClick.setOnLongClickListener {
            Utils.copyPasteContext(context, textView.text.toString(), context.getString(R.string.text_copy_paste))
            return@setOnLongClickListener true
        }
    }

    fun setText(text: String) {
        textView.text = text
    }

    fun getText(): String {
        return textView.text.toString()
    }
}