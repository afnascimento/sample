package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ButtonHorizontal : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var buttonIcon : ButtonIconRound

    private var tvDescription : TextView

    private var buttonComponentClick : View

    private var contentFaixa : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var iconEnum = IconEnums.CALENDARIO

        var textDescription = context.getString(R.string.inovacao)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonHorizontal, 0, 0
            )
            try {
                iconEnum = IconEnums.getIconEnumByStyleable(typedArray, R.styleable.ButtonHorizontal_julTextViewIcon)

                textDescription = Utils.getStringByStyleable(typedArray, R.styleable.ButtonHorizontal_btHorCompText, "")
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.button_horizontal_layout, this)

        buttonIcon = findViewById(R.id.btIconRound)
        buttonIcon.setIcon(iconEnum)

        tvDescription = findViewById(R.id.tvDescription)
        tvDescription.text = textDescription

        buttonComponentClick = findViewById(R.id.contentClick)
        buttonComponentClick.setOnClickListener {
            mListener?.onButtonClickListener(
                ButtonClickListenerModel(
                    mItem?.text ?: "",
                    mItem?.intencao ?: "",
                    mItem?.event ?: "",
                    mItem?.param ?: ""
                )
            )
        }

        contentFaixa = findViewById(R.id.contentFaixa)
    }

    data class Item(@SerializedName("icon") var icon: String,
                    @SerializedName("color") var color: String,
                    @SerializedName("text") var text: String,
                    @SerializedName("intencao") var intencao: String,
                    @SerializedName("event") var event: String,
                    @SerializedName("param") var param: String,
                    @SerializedName("faixa") var faixa : Boolean)

    private var mItem : Item? = null

    fun setItem(item: Item) {
        setIcon(item.icon)
        setColor(item.color)
        setText(item.text)
        setVisibleFaixa(item.faixa)
        mItem = item
    }

    fun setVisibleFaixa(visible: Boolean) {
        if (visible) {
            contentFaixa.visibility = View.VISIBLE
        } else {
            contentFaixa.visibility = View.GONE
        }
    }

    fun setIcon(iconHexa : String) {
        buttonIcon.setIcon(iconHexa)
    }

    fun setColor(colorHexa : String) {
        val color = Utils.getColorByHexa(colorHexa)
        if (color != 0) {
            tvDescription.setTextColor(color)
        }
    }

    fun setText(text: String) {
        tvDescription.text = StringUtils.trimToEmpty(text)
    }

    private var mListener : Listener? = null

    fun setListener(listener: Listener) {
        mListener = listener
    }

    interface Listener {
        fun onButtonClickListener(model: ButtonClickListenerModel)
    }
}