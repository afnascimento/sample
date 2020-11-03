package com.unilever.julia.ui.component

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.data.model.AdicionarAgendaModel
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ButtonComponent : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvIcon : JuliaTextViewIcon

    private var tvDescription : TextView

    private var buttonComponentClick : ConstraintLayout

    private var tvDescriptionCenter : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var iconEnum = IconEnums.FOODS_2
        var btText = context.getString(R.string.status_do_pedido)

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonComponent, 0, 0
            )
            try {
                iconEnum = IconEnums.getIconEnumByStyleable(typedArray, R.styleable.ButtonComponent_julTextViewIcon)

                btText = typedArray.getString(R.styleable.ButtonComponent_btcompText) ?: btText
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.button_component, this)

        tvIcon = findViewById(R.id.tvIcon)
        tvIcon.setIcon(iconEnum.iconHexa)

        tvDescription = findViewById(R.id.tvDescription)
        tvDescription.text = StringUtils.trimToEmpty(btText)

        buttonComponentClick = findViewById(R.id.buttonComponentClick)
        buttonComponentClick.setOnClickListener {
            mListener?.onButtonClickListener(tvDescription.text.toString(), mUrl, mIntencao)
            mAdicionarAgendaListener?.onButtonClickListener(mAdicionarAgendaModel)
        }

        tvDescriptionCenter = findViewById(R.id.tvDescriptionCenter)
    }

    fun setVisibilityIcon(visible: Boolean) {
        if (visible) {
            tvIcon.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
            tvDescriptionCenter.visibility = View.GONE
        } else {
            tvIcon.visibility = View.GONE
            tvDescription.visibility = View.GONE
            tvDescriptionCenter.visibility = View.VISIBLE
        }
    }

    fun setIcon(iconHexa : String) {
        tvIcon.setIcon(iconHexa)
    }

    fun setColor(colorHexa : String) {
        val color = Utils.getColorByHexa(colorHexa)
        if (color != 0) {
            tvIcon.setTextColor(color)
            tvDescription.setTextColor(color)
            tvDescriptionCenter.setTextColor(color)

            // background color stroke
            Utils.setColorStroke(2, color, buttonComponentClick.background)
        }
    }

    fun setText(text: String) {
        tvDescription.text = StringUtils.trimToEmpty(text)
        tvDescriptionCenter.text = StringUtils.trimToEmpty(text)
    }

    private var mUrl = ""

    fun setUrl(url: String) {
        mUrl = url
    }

    private var mIntencao = ""

    fun setIntencao(intencao: String) {
        mIntencao = intencao
    }

    private var mListener : Listener? = null

    fun setListener(listener: Listener) {
        mListener = listener
    }

    interface Listener {
        fun onButtonClickListener(text: String, url: String, intencao: String)
    }

    private var mAdicionarAgendaModel = AdicionarAgendaModel.Item()

    fun setAdicionarAgendaModel(model: AdicionarAgendaModel.Item) {
        mAdicionarAgendaModel = model
    }

    private var mAdicionarAgendaListener : AdicionarAgendaListener? = null

    fun setListener(listener: AdicionarAgendaListener) {
        mAdicionarAgendaListener = listener
    }

    interface AdicionarAgendaListener {
        fun onButtonClickListener(model: AdicionarAgendaModel.Item)
    }
}