package com.unilever.julia.components

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.lang.StringBuilder

class IpvHeaderClient : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var buttonRound : ButtonTextRound

    private var tvCode : TextView

    private var tvName : TextView

    private var tvDescription : TextView

    private var contentClick : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_header_client, this)
        buttonRound = findViewById(R.id.buttonRound)
        tvCode = findViewById(R.id.tvCode)
        tvName = findViewById(R.id.tvName)
        tvDescription = findViewById(R.id.tvDescription)
        contentClick = findViewById(R.id.contentClick)
        contentClick.setOnLongClickListener {
            val text = StringBuilder()
                .append(tvCode.text.toString())
                .append(" ")
                .append(tvName.text.toString())
                .toString()
            Utils.copyPasteContext(context, StringUtils.trim(text), context.getString(R.string.text_copy_paste))
            return@setOnLongClickListener true
        }
    }

    fun setCommodity(commodity: String) {
        buttonRound.setText(commodity)
    }

    fun setButtonColor(color: String) {
        buttonRound.setColorBackground(color)
    }

    fun setCode(code: String) {
        tvCode.text = code
    }

    fun setName(name: String) {
        tvName.text = name
    }

    fun setDescription(description: String) {
        if (description.isEmpty()) {
            tvDescription.visibility = View.GONE
        } else {
            tvDescription.text = description
            tvDescription.visibility = View.VISIBLE
        }
    }
}