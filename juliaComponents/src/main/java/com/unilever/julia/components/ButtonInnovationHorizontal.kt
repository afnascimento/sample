package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class ButtonInnovationHorizontal<T : ButtonInnovationHorizontal.Item> : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, LayoutEnum.MULTIPLE.ordinal)

    constructor(context: Context, layoutEnum: LayoutEnum) : this(context, null, layoutEnum.ordinal)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    enum class LayoutEnum(var resLayout : Int) {
        ONE(R.layout.button_inovation_single_layout),
        MULTIPLE(R.layout.button_inovation_many_layout)
    }

    private lateinit var buttonIcon : ButtonIconRound

    private lateinit var tvTitle : TextView

    private lateinit var tvProject : TextView

    private lateinit var buttonClick: View

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        var attrsIcon : IconEnums? = null

        var attrsText : String? = null

        var attrsProjects : Int? = null

        var attrsLayout : Int = defStyleAttr

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonInnovationHorizontal, 0, 0
            )
            try {
                attrsIcon = IconEnums.getIconEnumByStyleable(typedArray, R.styleable.ButtonInnovationHorizontal_julTextViewIcon)

                attrsText = Utils.getStringByStyleable(typedArray, R.styleable.ButtonInnovationHorizontal_btivText, "")

                attrsProjects = typedArray.getInt(R.styleable.ButtonInnovationHorizontal_btivProjects, 0)

                attrsLayout = typedArray.getInt(R.styleable.ButtonInnovationHorizontal_btivLayout, attrsLayout)
            } finally {
                typedArray.recycle()
            }
        }

        val layoutEnum = LayoutEnum.values()[attrsLayout]

        inflate(context, layoutEnum.resLayout, this)

        buttonIcon = findViewById(R.id.btIconRound)
        if (attrsIcon != null)
            setIcon(attrsIcon.iconHexa)

        tvTitle = findViewById(R.id.tvTitle)
        if (attrsText != null)
            setText(attrsText)

        tvProject = findViewById(R.id.tvProject)
        if (attrsText != null)
            setProjects(attrsProjects ?: 0)

        buttonClick = findViewById(R.id.contentClick)
        buttonClick.setOnClickListener {
            if (mItem != null) {
                mListener?.onButtonClickListener(mItem!!)
            }
        }
    }

    interface Item {
        fun icon(): String
        fun color(): String
        fun text(): String
        fun projects(): Int
    }

    private var mItem : T? = null

    fun setItem(item: T) {
        setIcon(item.icon())
        setColor(item.color())
        setText(item.text())
        setProjects(item.projects())
        mItem = item
    }

    fun setIcon(iconHexa : String) {
        buttonIcon.setIcon(iconHexa)
    }

    fun setColor(colorHexa : String) {
        val color = Utils.getColorByHexa(colorHexa)
        if (color != 0) {
            tvTitle.setTextColor(color)
            tvProject.setTextColor(color)
        }
    }

    fun setText(text: String) {
        tvTitle.text = StringUtils.trimToEmpty(text)
    }

    fun setProjects(projects: Int) {
        tvProject.text = context.getString(R.string.inovation_projects_, projects.toString())
    }

    private var mListener : Listener<T>? = null

    fun setListener(listener: Listener<T>) {
        mListener = listener
    }

    interface Listener<T> {
        fun onButtonClickListener(item: T)
    }
}