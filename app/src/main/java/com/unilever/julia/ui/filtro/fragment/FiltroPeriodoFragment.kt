package com.unilever.julia.ui.filtro.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unilever.julia.R
import com.unilever.julia.ui.component.JuliaFiltroDateComponent
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoActivity
import java.util.*

class FiltroPeriodoFragment : androidx.fragment.app.Fragment(), IFiltroFragment {

    var mFiltroActivity : FiltroPedidoActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mFiltroActivity = context as FiltroPedidoActivity
    }

    override fun onDetach() {
        super.onDetach()
        mFiltroActivity = null
    }

    lateinit var filtroDtInit : JuliaFiltroDateComponent

    lateinit var filtroDtEnd : JuliaFiltroDateComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filtro_periodo_fragment, container, false)

        filtroDtInit = view.findViewById(R.id.filtroDtInit)
        filtroDtInit.setTitle(getString(R.string.de))

        filtroDtEnd = view.findViewById(R.id.filtroDtEnd)
        filtroDtEnd.setTitle(getString(R.string.ate))

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dtNow = Date()
        filtroDtInit.setDate(mFiltroActivity?.getPeriodo()?.first ?: dtNow)
        filtroDtEnd.setDate(mFiltroActivity?.getPeriodo()?.second ?: dtNow)
    }

    override fun onResume() {
        super.onResume()
        mFiltroActivity?.setIFiltroFragment(this@FiltroPeriodoFragment)
    }

    override fun onLimparFiltro() {
        val dtNow = Date()
        filtroDtInit.setDate(dtNow)
        filtroDtEnd.setDate(dtNow)
        //mFiltroActivity?.setPeriodo(null)
    }

    override fun onAplicarFiltro() {
        val initDate = filtroDtInit.getDate()
        val endDate = filtroDtEnd.getDate()

        if (initDate.after(endDate)) {
            filtroDtInit.setError("data maior que data final")
            filtroDtEnd.setError("data menor que data inicio")
            return
        }
        filtroDtInit.clearError()
        filtroDtEnd.clearError()

        mFiltroActivity?.setPeriodo(Pair(initDate, endDate))
        mFiltroActivity?.popBackStackFragment()
    }
}