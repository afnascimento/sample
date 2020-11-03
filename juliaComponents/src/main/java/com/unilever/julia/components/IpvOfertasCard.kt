package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

class IpvOfertasCard : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvDistrictScore : TextView

    private var tvCoordinator : TextView

    private var tvDistrictName : TextView

    private var tvVendorScore : TextView

    private var tvVendorName : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.ipv_ofertas_card, this)
        tvDistrictScore = findViewById(R.id.tvDistrictScore)
        tvCoordinator = findViewById(R.id.tvCoordinator)
        tvDistrictName = findViewById(R.id.tvDistrictName)
        tvVendorScore = findViewById(R.id.tvVendorScore)
        tvVendorName = findViewById(R.id.tvVendorName)
    }

    fun setDistrictName(name: String) {
        if (StringUtils.containsIgnoreCase(name, "DISTRITO")) {
            val text = StringUtils.replace(StringUtils.upperCase(name), "DISTRITO", "")
            tvDistrictName.text = StringUtils.trim(text)
        } else {
            tvDistrictName.text = name
        }
    }

    fun setCordinatorName(name: String) {
        tvCoordinator.text = name
    }

    fun setVendorName(name: String) {
        tvVendorName.text = name
    }

    fun setDistrictScore(score: Double, colorCode: String) {
        setScore(tvDistrictScore, score, colorCode)
    }

    fun setVendorScore(score: Double, colorCode: String) {
        setScore(tvVendorScore, score, colorCode)
    }

    private fun setScore(textView: TextView, score: Double, colorCode: String) {
        Utils.setColorDrawable(colorCode, textView.background)
        textView.text = Utils.getTextPercent(score)
    }
}