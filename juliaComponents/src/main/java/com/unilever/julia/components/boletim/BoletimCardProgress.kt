package com.unilever.julia.components.boletim

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.unilever.julia.components.ButtonIconBadge
import com.unilever.julia.components.JuliaFiltroTabs
import com.unilever.julia.components.R
import com.unilever.julia.components.model.BoletimCard
import com.unilever.julia.components.model.BoletimItem
import com.unilever.julia.utils.NumberUtils
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.lang.StringBuilder

class BoletimCardProgress : CardView, JuliaFiltroTabs.Listener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvTerritory : TextView
    private var tvFilters : TextView
    private var tabs : JuliaFiltroTabs

    private var tvHeaderDescription : TextView
    private var tvHeaderValue : TextView
    private var tvHeaderPercentage : TextView

    private var percentEntradaDia : BoletimViewPercentage
    private var percentEntrada : BoletimViewPercentage
    private var percentFaturado : BoletimViewPercentage

    private var btnLeft : ButtonIconBadge
    private var btnRight : ButtonIconBadge

    interface Listener {
        fun onLeftClick(attendanceFilter: AttendanceFilter?, filters: BoletimFiltros?)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.boletim_card_progress, this)

        tvTerritory = findViewById(R.id.tvTerritory)
        tvFilters = findViewById(R.id.tvFilters)

        tabs = findViewById(R.id.tabs)
        tabs.setListener(this)

        tvHeaderDescription = findViewById(R.id.tvHeaderDescription)
        tvHeaderValue = findViewById(R.id.tvHeaderValue)
        tvHeaderPercentage = findViewById(R.id.tvHeaderPercentage)

        percentEntradaDia = findViewById(R.id.percentEntradaDia)
        percentEntrada = findViewById(R.id.percentEntrada)
        percentFaturado = findViewById(R.id.percentFaturado)

        btnLeft = findViewById(R.id.btnLeft)
        btnLeft.setOnClickListener {
            mListener?.onLeftClick(mAttendanceFilter, mFilters)
        }

        btnRight = findViewById(R.id.btnRight)
        btnRight.setOnClickListener {
            reset()
        }
    }

    fun reset() {
        if (mCardDefault != null) {
            updateBind(mCardDefault!!, null, null)
            btnLeft.setVisibleBagde(false)
            btnRight.isEnabled = false
        }
    }

    private var mCardDefault : BoletimCard? = null
    private var mCard : BoletimCard? = null

    fun initBind(card : BoletimCard) {
        // set value
        mCardDefault = card

        // update view
        update(card, null,null, true)

        // buttons
        btnLeft.setVisibleBagde(false)
        btnRight.isEnabled = false
    }

    private var mFilters: BoletimFiltros? = null

    private var mAttendanceFilter: AttendanceFilter? = null

    fun updateBind(card : BoletimCard, attendanceFilter: AttendanceFilter?, filters: BoletimFiltros?) {
        // update view
        update(card, attendanceFilter, filters, false)

        // buttons
        btnLeft.setVisibleBagde(true)
        btnRight.isEnabled = true
    }

    private fun update(card : BoletimCard, attendanceFilter: AttendanceFilter?, filters: BoletimFiltros?, firstUpdate: Boolean) {
        // update attendance
        mAttendanceFilter = attendanceFilter

        // set filters
        mFilters = filters

        // set value
        mCard = card

        // title
        var title = card.territory
        if (!firstUpdate) {
            if (attendanceFilter != null) {
                when {
                    StringUtils.isNotEmpty(attendanceFilter.territory) -> {
                        title = context.getString(R.string.territory_bulletin, card.territory)
                    }
                    StringUtils.isNotEmpty(attendanceFilter.district) -> {
                        title = context.getString(R.string.district_bulletin, card.territory)
                    }
                    StringUtils.isNotEmpty(attendanceFilter.hitRegional) -> {
                        title = context.getString(R.string.regional_bulletin, card.territory)
                    }
                    StringUtils.isNotEmpty(attendanceFilter.subsidiary) -> {
                        title = context.getString(R.string.subsidiary_bulletin, card.territory)
                    }
                }
            }
        }

        tvTerritory.text = title

        // filters
        if (filters == null) {
            tvFilters.visibility = View.GONE
        } else {
            val builder = StringBuilder()
            if (filters.channel.isNotEmpty()) {
                builder.append(" CANAL |")
            }
            if (filters.customer.isNotEmpty()) {
                builder.append(" CLIENTES |")
            }
            if (filters.commodity.isNotEmpty()) {
                builder.append(" COMMODITIES |")
            }
            if (filters.category.isNotEmpty()) {
                builder.append(" CATEGORIA |")
            }
            if (filters.brand.isNotEmpty()) {
                builder.append(" MARCAS |")
            }

            var text = builder.toString()
            if (text.isNotEmpty()) {
                text = StringUtils.removeEnd(text, "|")
                text = StringUtils.trim(text)

                tvFilters.visibility = View.VISIBLE
                tvFilters.text = context.getString(R.string.filtros_boletim, text)
            }
        }

        // tabs
        tabs.setTextTabRight(card.nivel?.header?.title ?: context.getString(R.string.niv))
        tabs.setTextTabRight(card.tonelada?.header?.title ?: context.getString(R.string.ton))

        onTabClick(0)
    }

    override fun onTabClick(index: Int) {
        if (index == 0) {
            if (mCard?.nivel != null) {
                updateTab(mCard?.nivel!!)
            }
        } else {
            if (mCard?.tonelada != null) {
                updateTab(mCard?.tonelada!!)
            }
        }
    }

    private fun updateTab(item: BoletimItem) {
        tvHeaderDescription.text = item.header.description
        tvHeaderValue.text = NumberUtils.toCurrencyBrazil(item.header.value, false)
        tvHeaderPercentage.text = context.getString(R.string.header_percentage, Utils.getTextPercent(item.header.percentage))

        if (item.entradaDia == null) {
            percentEntradaDia.visibility = View.GONE
        } else {
            percentEntradaDia.visibility = View.VISIBLE
            percentEntradaDia.bind(item.entradaDia!!, false)
        }

        if (item.entrada == null) {
            percentEntrada.visibility = View.GONE
        } else {
            percentEntrada.visibility = View.VISIBLE
            percentEntrada.bind(item.entrada!!, true)
        }

        if (item.faturado == null) {
            percentFaturado.visibility = View.GONE
        } else {
            percentFaturado.visibility = View.VISIBLE
            percentFaturado.bind(item.faturado!!, true)
        }
    }
}