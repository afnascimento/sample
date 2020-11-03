package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.google.gson.Gson
import com.unilever.julia.components.model.ButtonClickListenerModel
import com.unilever.julia.utils.Utils

class JuliaButtonsHorizontal : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (isInEditMode) {
            init(context, attrs)
        } else {
            init(context, null)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    lateinit var julAnswerCard : JuliaAnswerComponent

    lateinit var contentButtonItems : LinearLayout

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.julia_chat_button_horizontal, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentButtonItems = findViewById(R.id.contentButtonItems)

        if (attrs != null) {
            val items = Gson().fromJson(
                context.getString(R.string.button_horizontal_items),
                Array<ButtonHorizontal.Item>::class.java)

            addButtonItems(items.toMutableList(), object : ButtonHorizontal.Listener {
                override fun onButtonClickListener(model: ButtonClickListenerModel) {
                }
            })
        }
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun addButtonItems(items : MutableList<ButtonHorizontal.Item>, listener: ButtonHorizontal.Listener) {
        contentButtonItems.removeAllViews()
        var index = 0
        for (it in items) {
            val button = ButtonHorizontal(context)
            button.setItem(it)
            button.setListener(listener)

            if (items.size == 1) {
                //nothing
            } else if (items.size == 2) {
                if (index == 1) {
                    button.layoutParams = getMargins(24f, 0f, 0f, 0f)
                }
            } else if (items.size == 3) {
                if (index == 1) {
                    button.layoutParams = getMargins(16f, 0f, 16f, 0f)
                }
            } else {
                if (index == 0) {
                    button.layoutParams = getMargins(24f, 0f, 0f, 0f)
                } else if ((items.size - 1) == index) {
                    button.layoutParams = getMargins(12f, 0f, 24f, 0f)
                } else {
                    button.layoutParams = getMargins(12f, 0f, 0f, 0f)
                }
            }
            contentButtonItems.addView(button)
            index += 1
        }
    }

    private fun getMargins(left: Float, top: Float, right: Float, bottom: Float): LayoutParams {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        params.setMargins(getMargin(left), getMargin(top), getMargin(right), getMargin(bottom))
        return params
    }

    private fun getMargin(value: Float): Int {
        return Utils.getDimension(context.resources, value, TypedValue.COMPLEX_UNIT_DIP).toInt()
    }
}