package com.unilever.julia.components

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.unilever.julia.utils.Utils

class JuliaFiltroTabs : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tabRefinar : View

    private var tabOrdenacao : View

    private var tvTitleTab1 : TextView

    private var tvTitleTab2 : TextView

    private var strokeTab1 : View

    private var strokeTab2 : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val textLeft : String
        val textRight : String

        val typedArray = Utils.Styleable.getTypedArray(context, attrs, R.styleable.JuliaFiltroTabs)
        try {
            textLeft = Utils.Styleable.getString(typedArray,
                R.styleable.JuliaFiltroTabs_julFilTabLeftText, context.getString(R.string.refinar))

            textRight = Utils.Styleable.getString(typedArray,
                R.styleable.JuliaFiltroTabs_julFilTabRightText, context.getString(R.string.ordernar))
        } finally {
            typedArray?.recycle()
        }

        inflate(context, R.layout.julia_filtro_tabs, this)

        tabRefinar = findViewById(R.id.tabRefinar)
        tabRefinar.setOnClickListener {
            setActivite(0)
        }

        tabOrdenacao = findViewById(R.id.tabOrdenacao)
        tabOrdenacao.setOnClickListener {
            setActivite(1)
        }

        tvTitleTab1 = findViewById(R.id.tvTitleTab1)
        tvTitleTab2 = findViewById(R.id.tvTitleTab2)
        strokeTab1 = findViewById(R.id.strokeTab1)
        strokeTab2 = findViewById(R.id.strokeTab2)

        setTextTabLeft(textLeft)
        setTextTabRight(textRight)
    }

    fun setTextTabLeft(text: String) {
        tvTitleTab1.text = text
    }

    fun setTextTabRight(text: String) {
        tvTitleTab2.text = text
    }

    private var mTabSelected = 0

    fun setActivite(index: Int) {
        if (mTabSelected == index) {
            return
        }
        mTabSelected = index
        mListener?.onTabClick(mTabSelected)

        if (index == 0) {
            TextViewCompat.setTextAppearance(tvTitleTab1,
                R.style.tabs_text_active
            )
            strokeTab1.setBackgroundColor(ContextCompat.getColor(context,
                R.color.filtro_blue_accent
            ))

            TextViewCompat.setTextAppearance(tvTitleTab2,
                R.style.tabs_text_inactive
            )
            strokeTab2.setBackgroundColor(ContextCompat.getColor(context,
                R.color.filtro_cinza_text
            ))
        } else {
            TextViewCompat.setTextAppearance(tvTitleTab1,
                R.style.tabs_text_inactive
            )
            strokeTab1.setBackgroundColor(ContextCompat.getColor(context,
                R.color.filtro_cinza_text
            ))

            TextViewCompat.setTextAppearance(tvTitleTab2,
                R.style.tabs_text_active
            )
            strokeTab2.setBackgroundColor(ContextCompat.getColor(context,
                R.color.filtro_blue_accent
            ))
        }
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun onTabClick(index: Int)
    }
}