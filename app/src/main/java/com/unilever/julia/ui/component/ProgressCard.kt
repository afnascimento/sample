package com.unilever.julia.ui.component

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ProgressCard : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (isInEditMode) {
            init(context, attrs, 0)
        } else {
            init(context, null, 0)
        }
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    lateinit var contentItems : LinearLayout

    private fun getItemByAttrs(typedArray: TypedArray, indexPercent: Int, indexColor: Int): Item? {
        val percent = typedArray.getFloat(indexPercent, -1f)
        val colorHex = typedArray.getString(indexColor)
        if (percent > 0f && StringUtils.isNotEmpty(colorHex)) {
            return Item(percent.toString().toDouble(), colorHex)
        }
        return null
    }

    fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        inflate(context, R.layout.progress_card_layout, this)
        contentItems = findViewById(R.id.contentItems)

        val items = arrayListOf<Item>()
        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ProgressCard, 0, 0
            )
            try {
                val item1 = getItemByAttrs(
                    typedArray,
                    R.styleable.ProgressCard_pgCardPercent1,
                    R.styleable.ProgressCard_pgCardColorHex1
                )
                if (item1 != null) {
                    items.add(item1)
                }

                val item2 = getItemByAttrs(
                    typedArray,
                    R.styleable.ProgressCard_pgCardPercent2,
                    R.styleable.ProgressCard_pgCardColorHex2
                )
                if (item2 != null) {
                    items.add(item2)
                }

                val item3 = getItemByAttrs(
                    typedArray,
                    R.styleable.ProgressCard_pgCardPercent3,
                    R.styleable.ProgressCard_pgCardColorHex3
                )
                if (item3 != null) {
                    items.add(item3)
                }
            } finally {
                typedArray.recycle()
            }
        }

        if (items.isNotEmpty()) {
            addItems(items)
        }
    }

    fun addItems(items: MutableList<Item>) {
        contentItems.removeAllViewsInLayout()

        val list = arrayListOf<Percent>()
        var index = 0
        for (it in items) {
            if (it.value > 0.0) {
                val textPercent = Utils.getTextPercent(it.value)
                val widthSize = getWidthSize(it.value, textPercent)
                val hexColor = Utils.getColorByHexa(it.hexColor)
                list.add(Percent(index, textPercent, widthSize, hexColor))
                index++
            }
        }

        if (list.isNotEmpty()) {
            // sort widthSize asc
            val sortedBy = list.sortedBy { selectorWidth(it) }

            var widthProgress = mWidth100
            for (it in sortedBy) {
                if (it.widthSize > widthProgress) {
                    it.widthSize = widthProgress
                }
                widthProgress -= it.widthSize
            }

            val sortedIndexAsc = list.sortedBy { selectorIndex(it) }
            for (it in sortedIndexAsc) {
                contentItems.addView(getViewItem(it))
            }
        }
    }

    private fun selectorWidth(p: Percent): Double = p.widthSize

    private fun selectorIndex(p: Percent): Int = p.index

    private val mWidth2CharsMin = Utils.getDimension(resources, resources.getDimension(R.dimen.status_ped_carteira_progress_width_2chars_min), TypedValue.COMPLEX_UNIT_PX).toDouble()
    private val mWidth3CharsMin = Utils.getDimension(resources, resources.getDimension(R.dimen.status_ped_carteira_progress_width_3chars_min), TypedValue.COMPLEX_UNIT_PX).toDouble()
    private val mWidth4CharsMin = Utils.getDimension(resources, resources.getDimension(R.dimen.status_ped_carteira_progress_width_4chars_min), TypedValue.COMPLEX_UNIT_PX).toDouble()
    private val mWidth5CharsMin = Utils.getDimension(resources, resources.getDimension(R.dimen.status_ped_carteira_progress_width_5chars_min), TypedValue.COMPLEX_UNIT_PX).toDouble()
    private val mWidth6CharsMin = Utils.getDimension(resources, resources.getDimension(R.dimen.status_ped_carteira_progress_width_6chars_min), TypedValue.COMPLEX_UNIT_PX).toDouble()

    private val mWidth100 = Utils.getDimension(resources, resources.getDimension(R.dimen.status_ped_carteira_progress_width), TypedValue.COMPLEX_UNIT_PX).toDouble()

    private fun getWidthMin(textPercent : String) : Double {
        val charsSize = textPercent.length
        if (charsSize == 2) {
            return mWidth2CharsMin
        } else if (charsSize == 3) {
            return mWidth3CharsMin
        } else if (charsSize == 4) {
            return mWidth4CharsMin
        } else if (charsSize == 5) {
            return mWidth5CharsMin
        } else if (charsSize == 6) {
            return mWidth6CharsMin
        }
        return 0.0
    }

    private fun getWidthSize(percent: Double, textPercent : String): Double {
        // (width100 x porcentagem) / 100
        var widthItem = (mWidth100 * percent) / 100

        val widthMin = getWidthMin(textPercent)
        if (widthItem < widthMin) {
            widthItem = widthMin
        }

        return widthItem
    }

    private fun getViewItem(item: Percent): View {
        val view = LayoutInflater.from(context).inflate(R.layout.progress_card_item, contentItems, false)
        view.layoutParams = LayoutParams(item.widthSize.toInt(),  LayoutParams.MATCH_PARENT)

        val textview = view.findViewById<TextView>(R.id.tvTitle)
        textview.text = item.textPercent
        textview.setTextColor(item.hexColor)

        val viewProgress = view.findViewById<View>(R.id.viewProgress)
        viewProgress.setBackgroundColor(item.hexColor)

        return view
    }

    data class Item(var value: Double, var hexColor: String)

    private data class Percent(var index: Int, var textPercent: String, var widthSize : Double, var hexColor: Int)
}