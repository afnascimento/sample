package com.unilever.julia.ui.filtro.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unilever.julia.R
import com.unilever.julia.ui.component.JuliaFiltroDateComponent
import com.unilever.julia.ui.component.JuliaFiltroMoneyComponent
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoActivity
import java.util.*

class FiltroValoresFragment : androidx.fragment.app.Fragment(), IFiltroFragment {

    var mFiltroActivity : FiltroPedidoActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mFiltroActivity = context as FiltroPedidoActivity
    }

    override fun onDetach() {
        super.onDetach()
        mFiltroActivity = null
    }

    lateinit var moneyInit : JuliaFiltroMoneyComponent

    lateinit var moneyEnd : JuliaFiltroMoneyComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filtro_valores_fragment, container, false)

        moneyInit = view.findViewById(R.id.moneyInit)
        moneyInit.setTitle(getString(R.string.minimo))

        moneyEnd = view.findViewById(R.id.moneyEnd)
        moneyEnd.setTitle(getString(R.string.maximo))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moneyInit.setValue(mFiltroActivity?.getValores()?.first ?: 0.00)
        moneyEnd.setValue(mFiltroActivity?.getValores()?.second ?: 0.00)
    }

    override fun onResume() {
        super.onResume()
        mFiltroActivity?.setIFiltroFragment(this@FiltroValoresFragment)
    }

    override fun onLimparFiltro() {
        moneyInit.setValue(0.00)
        moneyEnd.setValue(0.00)
    }

    override fun onAplicarFiltro() {
        val initValue = moneyInit.getValue()
        val endValue = moneyEnd.getValue()

        if (initValue > endValue) {
            moneyInit.setError(getString(R.string.valores_error_minimo))
            moneyEnd.setError(getString(R.string.valores_error_maximo))
            return
        }
        moneyInit.clearError()
        moneyEnd.clearError()

        mFiltroActivity?.setValores(Pair(initValue, endValue))
        mFiltroActivity?.popBackStackFragment()
    }
}