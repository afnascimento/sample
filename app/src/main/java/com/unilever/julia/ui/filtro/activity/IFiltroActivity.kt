package com.unilever.julia.ui.filtro.activity

import com.unilever.julia.data.enums.FiltroOrdenacaoEnum
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoActivity
import com.unilever.julia.ui.filtro.fragment.IFiltroFragment
import java.util.*

interface IFiltroActivity {

    fun loadFragment(fragmentType: FiltroPedidoActivity.FragmentType)
    fun setIFiltroFragment(filtroFragment: IFiltroFragment)
    fun setPeriodo(periodo: Pair<Date, Date>?)
    fun getPeriodo(): Pair<Date, Date>?
    fun popBackStackFragment()
    fun setValores(periodo: Pair<Double, Double>?)
    fun getValores(): Pair<Double, Double>?
    fun setResultFinish()
    fun setOrdenacao(ordenacao: FiltroOrdenacaoEnum?)
    fun getOrdenacao(): FiltroOrdenacaoEnum?
}