package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.unilever.julia.utils.Utils

class IpvTabButton : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var contentClick : View

    private var viewColor : View

    private var strokeTab : View

    private var tvText : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_tab_button, this)

        contentClick = findViewById(R.id.contentClick)

        viewColor = findViewById(R.id.viewColor)

        strokeTab = findViewById(R.id.strokeTab)

        tvText = findViewById(R.id.tvTitle)
    }

    fun setText(text: String) {
        tvText.text = text
    }

    fun setColorRound(colorHexa: String) {
        Utils.setColorDrawable(colorHexa, viewColor.background)
    }

    fun setActive(active: Boolean) {
        if (active) {
            TextViewCompat.setTextAppearance(tvText, R.style.tabs_text_active)
            strokeTab.setBackgroundColor(ContextCompat.getColor(context, R.color.filtro_blue_accent))
        } else {
            TextViewCompat.setTextAppearance(tvText, R.style.tabs_text_inactive)
            strokeTab.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }

    interface Listener {
        fun onIpvButtonClick(index: Int, tabButton: IpvTabButton)
    }

    fun setListener(index: Int, listener: Listener) {
        contentClick.setOnClickListener {
            listener.onIpvButtonClick(index, this)
        }
    }
}