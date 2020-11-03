package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.utils.NumberUtils

class JuliaStatusPedidoCarteira : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var answerCard : JuliaAnswerComponent

    private var progressValueOrders : ProgressCard

    private var progressBoxOrders : ProgressCard

    private var tvTitleTerritorio : TextView

    private var tvTitleQtdClientes : TextView

    private var tvTotalPedidos : TextView

    private var tvValorTotalPedido : TextView

    private var tvTotalCaixas : TextView

    private var contentCard : View

    private var buttonArrow : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.status_pedido_carteira_layout, this)

        answerCard = findViewById(R.id.julAnswerCard)

        progressValueOrders = findViewById(R.id.progressValueOrders)

        progressBoxOrders = findViewById(R.id.progressBoxOrders)

        tvTitleTerritorio = findViewById(R.id.tvTitleTerritorio)

        tvTitleQtdClientes = findViewById(R.id.tvTitleQtdClientes)

        tvTotalPedidos= findViewById(R.id.tvTotalPedidos)

        tvValorTotalPedido = findViewById(R.id.tvValorTotalPedido)

        tvTotalCaixas = findViewById(R.id.tvTotalCaixas)

        contentCard = findViewById(R.id.contentCard)

        buttonArrow = findViewById(R.id.buttonArrow)
        buttonArrow.setOnClickListener {
            mListener?.onNextClick(mIntencao, mTerritory)
        }
    }

    private var mIntencao = ""

    private var mTerritory = ""

    fun setItemNext(intencao: String, territory: String) {
        mIntencao = intencao
        mTerritory = territory
    }

    fun showCard() {
        contentCard.visibility = View.VISIBLE
    }

    fun hideCard() {
        contentCard.visibility = View.GONE
    }

    fun setQtdeCaixas(qtde: Int) {
        tvTotalCaixas.text = qtde.toString()
    }

    fun setValorTotalPedido(valor: Double) {
        tvValorTotalPedido.text = NumberUtils.toCurrencyBrazil(valor, true)
    }

    fun setQtdePedidos(qtde: Int) {
        tvTotalPedidos.text = qtde.toString()
    }

    fun setTerritorio(text: String) {
        tvTitleTerritorio.text = text
    }

    fun setQtdeClientes(qtde: Int) {
        tvTitleQtdClientes.text = qtde.toString()
    }

    fun setText(text : String) {
        answerCard.setText(text)
    }

    fun addProgressValueOrders(items: MutableList<ProgressCard.Item>) {
        progressValueOrders.addItems(items)
    }

    fun addProgressBoxOrders(items: MutableList<ProgressCard.Item>) {
        progressBoxOrders.addItems(items)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }

    interface Listener {
        fun onNextClick(intencao: String, territory: String)
    }
}