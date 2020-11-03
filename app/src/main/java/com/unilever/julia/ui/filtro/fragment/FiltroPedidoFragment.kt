package com.unilever.julia.ui.filtro.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unilever.julia.R
import com.unilever.julia.ui.component.JuliaFiltroItemComponent
import com.unilever.julia.ui.filtro.activity.IFiltroActivity
import com.unilever.julia.ui.filtro.activity.pedido.FiltroPedidoActivity
import java.util.*

class FiltroPedidoFragment : androidx.fragment.app.Fragment(), IFiltroFragment {

    var mFiltroActivity : IFiltroActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mFiltroActivity = context as IFiltroActivity
    }

    override fun onDetach() {
        super.onDetach()
        mFiltroActivity = null
    }

    lateinit var itemPeriodo : JuliaFiltroItemComponent

    lateinit var itemValores : JuliaFiltroItemComponent

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.filtro_pedidos_fragment, container, false)

        itemPeriodo = view.findViewById(R.id.itemPeriodo)
        itemPeriodo.setListener(object : JuliaFiltroItemComponent.Listener {
            override fun onRedirectFilter() {
                mFiltroActivity?.loadFragment(FiltroPedidoActivity.FragmentType.PERIODO)
            }
        })

        itemValores = view.findViewById(R.id.itemValores)
        itemValores.setListener(object : JuliaFiltroItemComponent.Listener {
            override fun onRedirectFilter() {
                mFiltroActivity?.loadFragment(FiltroPedidoActivity.FragmentType.VALORES)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mFiltroActivity?.setIFiltroFragment(this@FiltroPedidoFragment)
        if (mFiltroActivity?.getPeriodo() == null) {
            itemPeriodo.clearFilter()
        } else {
            itemPeriodo.setValues(mFiltroActivity?.getPeriodo()!!, JuliaFiltroItemComponent.Type.DATE)
        }

        if (mFiltroActivity?.getValores() == null) {
            itemValores.clearFilter()
        } else {
            itemValores.setValues(mFiltroActivity?.getValores()!!, JuliaFiltroItemComponent.Type.MONEY)
        }
    }

    override fun onLimparFiltro() {
        itemPeriodo.clearFilter()
        itemValores.clearFilter()
    }

    override fun onAplicarFiltro() {
        mFiltroActivity?.setPeriodo(itemPeriodo.getValues() as Pair<Date, Date>?)
        mFiltroActivity?.setValores(itemValores.getValues() as Pair<Double, Double>?)
        mFiltroActivity?.setResultFinish()
    }
}