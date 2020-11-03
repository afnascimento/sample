package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonWrapContent
import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.utils.setAbreviateText

class JuliaInovacaoProjetoCard : LinearLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    var btnInovacaoProjetoDetalhe : ButtonWrapContent

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_inovacao_projeto_card, this)

        btnInovacaoProjetoDetalhe = findViewById(R.id.btnInovacaoProjetoDetalhe)

        btnInovacaoProjetoDetalhe.setOnClickListener {
            if (mItem != null) {
                mOnButtonClickListener?.onButtonDetailClick(mItem!!)
            }
        }
    }

    private var mItem : InovacaoProjetoModel? = null

    fun setItem(it : InovacaoProjetoModel) {
        mItem = it

        findViewById<TextView>(R.id.tvProjeto).text = it.projetoInovacao

        findViewById<TextView>(R.id.tvLancamentoData).text = it.dataLancamento

        findViewById<TextView>(R.id.tvCanaisValor).text = it.canal
    }

    fun setAbreviateTextProject() {
        findViewById<TextView>(R.id.tvProjeto).setAbreviateText(1)
    }

    fun setVisibleButton(visible : Boolean) {
        if (visible)
            btnInovacaoProjetoDetalhe.visibility = View.VISIBLE
        else
            btnInovacaoProjetoDetalhe.visibility = View.GONE
    }

    private var mOnButtonClickListener : Listener? = null

    fun setListener(listener : Listener) {
        mOnButtonClickListener = listener
    }

    interface Listener {
        fun onButtonDetailClick(item : InovacaoProjetoModel)
    }
}