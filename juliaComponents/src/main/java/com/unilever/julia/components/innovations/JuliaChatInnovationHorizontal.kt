package com.unilever.julia.components.innovations

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.ButtonInnovationHorizontal
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.components.R

class JuliaChatInnovationHorizontal : RelativeLayout {

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

    private lateinit var julAnswerCard : JuliaAnswerComponent

    private lateinit var contentButtonItems : LinearLayout

    private fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.julia_innovation_chat_horizontal, this)

        julAnswerCard = findViewById(R.id.julAnswerCard)

        contentButtonItems = findViewById(R.id.contentButtonItems)

        if (attrs != null) {
            val items = Gson().fromJson(
                context.getString(R.string.innovation_horizontal_items),
                Array<Item>::class.java)

            addButtonItems(items.toMutableList(), object :
                ButtonInnovationHorizontal.Listener<Item> {
                override fun onButtonClickListener(item: Item) {
                }
            })
        }
    }

    internal class Item : ButtonInnovationHorizontal.Item {

        @SerializedName("icon")
        var icon: String = ""

        @SerializedName("color")
        var color: String = ""

        @SerializedName("text")
        var text: String = ""

        @SerializedName("projects")
        var projects : Int = 0

        override fun icon(): String {
            return icon
        }

        override fun color(): String {
            return color
        }

        override fun text(): String {
            return text
        }

        override fun projects(): Int {
            return projects
        }
    }

    fun setText(text : String) {
        julAnswerCard.setText(text)
    }

    fun <T : ButtonInnovationHorizontal.Item> addButtonItems(items : MutableList<T>, listener: ButtonInnovationHorizontal.Listener<T>) {
        contentButtonItems.removeAllViews()

        if (items.size == 1) {
            val button = ButtonInnovationHorizontal<T>(
                context,
                ButtonInnovationHorizontal.LayoutEnum.ONE
            )
            button.setItem(items[0])
            button.setListener(listener)
            button.layoutParams = getMargins(
                resources.getDimension(R.dimen.julia_innovation_horizontal_one_margin_left),
                0f,
                resources.getDimension(R.dimen.julia_innovation_horizontal_one_margin_right),
                0f)
            contentButtonItems.addView(button)
            return
        }

        if (items.size == 2) {
            var index = 0

            for (it in items) {

                val button = ButtonInnovationHorizontal<T>(
                    context,
                    ButtonInnovationHorizontal.LayoutEnum.MULTIPLE
                )
                button.setItem(it)
                button.setListener(listener)

                if (index == 0) {
                    button.layoutParams = getMargins(
                        resources.getDimension(R.dimen.julia_innovation_horizontal_two_margin_left),
                        0f,
                        0f,
                        0f)
                } else {
                    button.layoutParams = getMargins(
                        resources.getDimension(R.dimen.julia_innovation_horizontal_two_margin_left_between),
                        0f,
                        0f,
                        0f)
                }

                contentButtonItems.addView(button)
                index += 1
            }
            return
        }

        if (items.isNotEmpty()) {
            var index = 0

            for (it in items) {
                val lastItem = (items.size - 1) == index

                val button = ButtonInnovationHorizontal<T>(
                    context,
                    ButtonInnovationHorizontal.LayoutEnum.MULTIPLE
                )
                button.setItem(it)
                button.setListener(listener)

                if (index == 0) {
                    button.layoutParams = getMargins(
                        resources.getDimension(R.dimen.julia_innovation_horizontal_multiple_margin_left),
                        0f,
                        0f,
                        0f)
                } else if (lastItem) {
                    button.layoutParams = getMargins(
                        resources.getDimension(R.dimen.julia_innovation_horizontal_multiple_margin_left_between),
                        0f,
                        resources.getDimension(R.dimen.julia_innovation_horizontal_multiple_margin_right),
                        0f)
                } else {
                    button.layoutParams = getMargins(
                        resources.getDimension(R.dimen.julia_innovation_horizontal_multiple_margin_left_between),
                        0f,
                        0f,
                        0f)
                }

                contentButtonItems.addView(button)
                index += 1
            }
        }
    }

    private fun getMargins(left: Float, top: Float, right: Float, bottom: Float): LayoutParams {
        val params = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        val marginLeft = left.toInt()//getDimension(left)
        val marginTop = top.toInt()//getDimension(top)
        val marginRight = right.toInt()//getDimension(right)
        val marginBottom = bottom.toInt()//getDimension(bottom)
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom)
        return params
    }

    //private fun getDimension(value: Float): Int {
    //    return Utils.getDimension(context.resources, value, TypedValue.COMPLEX_UNIT_DIP).toInt()
    //}
}