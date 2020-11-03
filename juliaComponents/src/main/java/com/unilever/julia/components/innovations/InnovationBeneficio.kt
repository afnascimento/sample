package com.unilever.julia.components.innovations

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.components.ButtonBooleanCheck
import com.unilever.julia.components.R
import com.unilever.julia.utils.bind

class InnovationBeneficio : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private val strokeTop by bind<View>(R.id.strokeTop)

    private val tvTitle by bind<TextView>(R.id.tvTitle)

    private val tvDescription by bind<TextView>(R.id.tvDescription)

    private val booleanButton by bind<ButtonBooleanCheck>(R.id.booleanButton)

    private fun init(context: Context, attrs: AttributeSet?) {
        var isStroke = true
        var title = ""
        var desc = ""

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.InnovationBeneficio, 0, 0
            )
            try {
                isStroke = typedArray.getBoolean(R.styleable.InnovationBeneficio_innoBen_isTopStroke, isStroke)
                title = typedArray.getString(R.styleable.InnovationBeneficio_innoBen_title) ?: ""
                desc = typedArray.getString(R.styleable.InnovationBeneficio_innoBen_description) ?: ""
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.innovation_beneficio, this)

        booleanButton.setListener(object : ButtonBooleanCheck.Listener {
            override fun onChangeBooleanButton(checked: ButtonBooleanCheck.Type) {
                updateDescription(checked)
            }
        })

        setVisibleStroke(isStroke)
        setTitle(title)
        setDescription(desc, desc)
    }

    fun setVisibleStroke(visible: Boolean) {
        strokeTop.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    private var mTextCustomer = ""
    private var mTextShopKeeper = ""

    fun setDescription(textCustomer: String, textShopKeeper : String) {
        mTextCustomer = textCustomer
        mTextShopKeeper = textShopKeeper
        updateDescription(booleanButton.getChecked())
    }

    private fun updateDescription(checked: ButtonBooleanCheck.Type) {
        when (checked) {
            ButtonBooleanCheck.Type.LEFT -> InnovationUtils.setDescription(tvDescription, mTextShopKeeper)
            ButtonBooleanCheck.Type.RIGHT -> InnovationUtils.setDescription(tvDescription, mTextCustomer)
        }
    }
}