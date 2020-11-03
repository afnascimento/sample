package com.unilever.julia.ui.component

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import android.util.AttributeSet
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.utils.NumberUtils

class JuliaFiltroMoneyComponent : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvTitle : TextView

    private var textInput : TextInputLayout

    private var editMoney : CurrencyEditText

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_filter_money, this)

        tvTitle = findViewById(R.id.tvTitle)

        textInput = findViewById(R.id.textInputMoney)

        editMoney = findViewById(R.id.editMoney)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setValue(value: Double) {
        editMoney.setValue(value)
    }

    fun getValue(): Double {
        return NumberUtils.toDouble(editMoney.text.toString())
    }

    fun setError(error: String) {
        textInput.error = error
    }

    fun clearError() {
        textInput.error = null
    }
}