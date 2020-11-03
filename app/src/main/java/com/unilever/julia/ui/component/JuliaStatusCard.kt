package com.unilever.julia.ui.component

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonWrapContent
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.logger.Logger
import com.unilever.julia.utils.NumberUtils
import com.unilever.julia.utils.Utils
import com.unilever.julia.utils.setAbreviateText
import org.apache.commons.lang3.StringUtils

class JuliaStatusCard : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, ENABLED_SHARE)

    private var btnDetail : ButtonWrapContent

    private var tvCliente : TextView

    private var tvStatusDesc : TextView

    private var tvIconMoney : JuliaTextViewIcon

    private var tvIconCaixa : JuliaTextViewIcon

    private var tvStatusIcon : JuliaTextViewIcon

    private var tvValor : TextView

    private var tvCaixaValor : TextView

    private var tvCaixaDesc : TextView

    private var contentCard : ConstraintLayout

    private var juliaStatusCardLineLeft : View

    private var contentOverlay : View

    companion object {
        val ENABLED_SHARE = 0
        val DISABLED_SHARE = 1
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_status_card, this)

        val enabledShare = (defStyleAttr == ENABLED_SHARE)

        btnDetail = findViewById(R.id.btnDetail)

        btnDetail.setOnClickListener {
            if (mOnLongClick) {
                mOnButtonClickListener?.onButtonDetailClick(mItem)
            }
        }

        tvCliente = findViewById(R.id.tvCliente)

        tvStatusDesc = findViewById(R.id.tvStatusDesc)

        tvIconMoney = findViewById(R.id.tvIconMoney)

        tvIconCaixa = findViewById(R.id.tvIconCaixa)

        tvStatusIcon = findViewById(R.id.tvStatusIcon)

        tvValor = findViewById(R.id.tvValor)

        tvCaixaValor = findViewById(R.id.tvCaixaValor)

        tvCaixaDesc = findViewById(R.id.tvCaixaDesc)

        contentCard = findViewById(R.id.contentCard)

        juliaStatusCardLineLeft  = findViewById(R.id.juliaStatusCardLineLeft)

        contentOverlay = findViewById(R.id.contentOverlay)

        if (enabledShare) {
            contentCard.setOnClickListener {
                if (!mOnLongClick) {
                    Logger.debug("CARD", "OnClickListener")

                    if (contentOverlay.visibility == View.VISIBLE) {
                        setCardSelected(false)
                    } else {
                        setCardSelected(true)
                    }

                    mOnClickListener?.onClickListener(this@JuliaStatusCard)
                }
            }

            contentCard.setOnLongClickListener {
                if (mOnLongClick) {
                    Logger.debug("CARD", "OnLongClickListener")

                    setCardSelected(true)

                    mOnLongClick = false

                    mOnClickListener?.onLongClickListener(this@JuliaStatusCard)
                }
                return@setOnLongClickListener true
            }
        }
    }

    fun setCardSelected(visible: Boolean) {
        if (visible) {
            contentOverlay.visibility = View.VISIBLE
        } else {
            contentOverlay.visibility = View.GONE
        }
    }

    private var mOnLongClick = true

    fun setLongClick(isOnLongClick : Boolean) {
        mOnLongClick = isOnLongClick
    }

    fun setVisibleButton(visible: Boolean) {
        btnDetail.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun isCardSelected(): Boolean {
        return contentOverlay.visibility == View.VISIBLE
    }

    private var mItem : StatusPedidoModel.Item = StatusPedidoModel.Item()

    fun getItem(): StatusPedidoModel.Item {
        return mItem
    }

    fun setItem(it : StatusPedidoModel.Item) {
        mItem = it

        tvCliente.text = StringUtils.trimToEmpty(it.order?.descCustomer)

        tvStatusDesc.text = StringUtils.trimToEmpty(it.getStatusPedidoText(context))

        tvIconMoney.setIcon(it.getIconMoney())

        tvIconCaixa.setIcon(it.getIconCaixa())

        tvStatusIcon.setIcon(it.getIconStatus())

        tvValor.text = it.order?.getValorPedido()

        val amount = NumberUtils.toInteger(it.order?.amount)

        tvCaixaValor.text = amount.toString()

        tvCaixaDesc.text = if (amount <= 1) context.getString(R.string.caixa) else context.getString(R.string.caixas)

        setColor(StringUtils.trimToEmpty(it.order?.color))
    }

    fun setAbreviateTextClient() {
        tvCliente.setAbreviateText(1)
    }

    fun setColor(colorHexa : String) {
        val color = Utils.getColorByHexa(colorHexa)
        if (color != 0) {
            // contentCard stroke
            Utils.setColorStroke(2, color, contentCard.background)

            // line left
            Utils.setColorDrawable(color, juliaStatusCardLineLeft.background)

            // text views
            tvCliente.setTextColor(color)

            tvIconMoney.setTextColor(color)

            tvIconCaixa.setTextColor(color)

            tvStatusIcon.setTextColor(color)

            tvStatusDesc.setTextColor(color)

            btnDetail.setColor(color)
        }
    }

    private var mOnButtonClickListener : Listener? = null

    fun setListener(listener : Listener) {
        mOnButtonClickListener = listener
    }

    interface Listener {
        fun onButtonDetailClick(item : StatusPedidoModel.Item)
    }

    private var mOnClickListener : CardClickListener? = null

    fun setCardClickListener(listener : CardClickListener) {
        mOnClickListener = listener
    }

    interface CardClickListener {
        fun onClickListener(card : JuliaStatusCard)
        fun onLongClickListener(card : JuliaStatusCard)
    }
}