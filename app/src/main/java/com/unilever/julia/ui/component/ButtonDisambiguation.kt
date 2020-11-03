package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonIconRound
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.data.model.ButtonDisambiguationModel
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ButtonDisambiguation : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var buttonIcon : ButtonIconRound

    private var tvDescription : TextView

    private var buttonComponentClick : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var iconEnum = IconEnums.CALENDARIO

        var textDescription = context.getString(R.string.meu_territorio)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonDisambiguation, 0, 0
            )
            try {
                iconEnum = IconEnums.getIconEnumByStyleable(typedArray, R.styleable.ButtonDisambiguation_julTextViewIcon)

                textDescription = Utils.getStringByStyleable(typedArray, R.styleable.ButtonDisambiguation_btDisambText, "")
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.disambiguation_button, this)

        buttonIcon = findViewById(R.id.btIconRound)
        buttonIcon.setIcon(iconEnum)

        tvDescription = findViewById(R.id.tvDescription)
        tvDescription.text = textDescription

        buttonComponentClick = findViewById(R.id.contentClick)
        buttonComponentClick.setOnClickListener {
            mListener?.onButtonClickListener(mItem.clickButton)
        }
    }

    fun setIcon(iconHexa : String) {
        buttonIcon.setIcon(iconHexa)
    }

    fun setText(text: String) {
        tvDescription.text = StringUtils.trimToEmpty(text)
    }

    private var mItem = ButtonDisambiguationModel.Item()

    fun setItem(item : ButtonDisambiguationModel.Item) {
        mItem = item
        setIcon(item.icon)
        setText(item.clickButton.text)
    }

    private var mListener : IButtonClickListener? = null

    fun setListener(listener: IButtonClickListener) {
        mListener = listener
    }
}