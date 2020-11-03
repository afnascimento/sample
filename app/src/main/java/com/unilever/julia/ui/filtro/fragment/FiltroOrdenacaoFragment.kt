package com.unilever.julia.ui.filtro.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.unilever.julia.R
import com.unilever.julia.data.enums.FiltroOrdenacaoEnum
import com.unilever.julia.ui.filtro.activity.IFiltroActivity

class FiltroOrdenacaoFragment : androidx.fragment.app.Fragment(), IFiltroFragment {

    var mFiltroActivity : IFiltroActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mFiltroActivity = context as IFiltroActivity
    }

    override fun onDetach() {
        super.onDetach()
        mFiltroActivity = null
    }

    lateinit var rgOption : RadioGroup

    lateinit var rbtMenorValor : RadioButton

    lateinit var rbtMaiorValor : RadioButton

    lateinit var rbtDtMaisRecente : RadioButton

    lateinit var rbtDtMenosRecente : RadioButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filtro_ordenacao_fragment, container, false)
        rgOption = view.findViewById(R.id.rgOption)
        rgOption.setOnCheckedChangeListener { _, checkedId ->
            setRadioChecked(checkedId)
        }

        rbtMenorValor = view.findViewById(R.id.rbtMenorValor)

        rbtMaiorValor = view.findViewById(R.id.rbtMaiorValor)

        rbtDtMaisRecente = view.findViewById(R.id.rbtDtMaisRecente)

        rbtDtMenosRecente = view.findViewById(R.id.rbtDtMenosRecente)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mFiltroActivity?.setIFiltroFragment(this@FiltroOrdenacaoFragment)

        if (mFiltroActivity?.getOrdenacao() == null) {
            rgOption.check(R.id.rbtDtMenosRecente)
        } else if (mFiltroActivity?.getOrdenacao() == FiltroOrdenacaoEnum.MENOR_VALOR) {
            rgOption.check(R.id.rbtMenorValor)
        } else if (mFiltroActivity?.getOrdenacao() == FiltroOrdenacaoEnum.MAIOR_VALOR) {
            rgOption.check(R.id.rbtMaiorValor)
        } else if (mFiltroActivity?.getOrdenacao() == FiltroOrdenacaoEnum.DATA_MAIS_RECENTE) {
            rgOption.check(R.id.rbtDtMaisRecente)
        } else {
            rgOption.check(R.id.rbtDtMenosRecente)
        }
    }

    override fun onLimparFiltro() {
        rgOption.check(R.id.rbtDtMenosRecente)
        mFiltroActivity?.setOrdenacao(null)
    }

    override fun onAplicarFiltro() {
        mFiltroActivity?.popBackStackFragment()
        mFiltroActivity?.setResultFinish()
    }

    private fun setRadioChecked(checkedId: Int) {
        if (checkedId == R.id.rbtMenorValor) {
            mFiltroActivity?.setOrdenacao(FiltroOrdenacaoEnum.MENOR_VALOR)
        } else if (checkedId == R.id.rbtMaiorValor) {
            mFiltroActivity?.setOrdenacao(FiltroOrdenacaoEnum.MAIOR_VALOR)
        } else if (checkedId == R.id.rbtDtMaisRecente) {
            mFiltroActivity?.setOrdenacao(FiltroOrdenacaoEnum.DATA_MAIS_RECENTE)
        } else if (checkedId == R.id.rbtDtMenosRecente) {
            mFiltroActivity?.setOrdenacao(FiltroOrdenacaoEnum.DATA_MENOS_RECENTE)
        }
    }
}