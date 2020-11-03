package com.unilever.julia.ui.component

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.utils.NumberUtils
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.util.*

class JuliaFiltroItemComponent : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvTitle : TextView

    private var contentClick : View

    private var itemSelected : View

    private var tvItemSelValue : TextView

    private var btnDelete : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var title = context.getString(R.string.escolher_periodo)

        var valueLeft = "01/02/2019"

        var valueRight = "28/02/2019"

        var valuesVisible = false

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.JuliaFiltroItemComponent, 0, 0
            )
            try {
                val tempTitle = typedArray.getString(R.styleable.JuliaFiltroItemComponent_julFilItemTitle)
                if (StringUtils.isNotBlank(tempTitle)) {
                    title = StringUtils.trimToEmpty(tempTitle)
                }

                val tempValueLeft = typedArray.getString(R.styleable.JuliaFiltroItemComponent_julFilItemValueLeft)
                if (StringUtils.isNotBlank(tempValueLeft)) {
                    valueLeft = StringUtils.trimToEmpty(tempValueLeft)
                }

                val tempValueRight = typedArray.getString(R.styleable.JuliaFiltroItemComponent_julFilItemValueRight)
                if (StringUtils.isNotBlank(tempValueRight)) {
                    valueRight = StringUtils.trimToEmpty(tempValueRight)
                }

                valuesVisible = typedArray.getBoolean(R.styleable.JuliaFiltroItemComponent_julFilItemValuesVisible, valuesVisible)
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.julia_filtro_item, this)

        tvTitle = findViewById(R.id.tvTitle)
        tvTitle.text = title

        contentClick = findViewById(R.id.contentClick)
        contentClick.setOnClickListener {
            mListener?.onRedirectFilter()
        }

        tvItemSelValue = findViewById(R.id.tvItemSelValue)
        tvItemSelValue.text = "$valueLeft - $valueRight"

        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener {
            clearFilter()
        }

        itemSelected = findViewById(R.id.itemSelected)

        setVisibilityValues(valuesVisible)
    }

    fun clearFilter() {
        mValues = null
        setVisibilityValues(false)
    }

    fun setVisibilityValues(visible: Boolean) {
        itemSelected.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun onRedirectFilter()
    }

    private var mValues : Pair<Any, Any>? = null

    fun getValues(): Pair<Any, Any>? {
        return mValues
    }

    enum class Type {
        DATE, MONEY
    }

    fun setValues(values: Pair<Any, Any>, type: Type) {
        var text = ""
        if (type == Type.DATE) {
            val left = Utils.toStringByDate(values.first as Date, "dd/MM/yyyy")
            val right = Utils.toStringByDate(values.second as Date, "dd/MM/yyyy")
            text = "$left - $right"
        } else if (type == Type.MONEY) {
            val left = NumberUtils.toCurrencyBrazil(values.first as Double, true)
            val right = NumberUtils.toCurrencyBrazil(values.second as Double, true)
            text = "$left - $right"
        }
        tvItemSelValue.text = text
        setVisibilityValues(true)
        mValues = values
    }
}