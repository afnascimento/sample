package com.unilever.julia.ui.filtro.activity.pedido

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.ButtonsBottom
import com.unilever.julia.data.enums.FiltroOrdenacaoEnum
import com.unilever.julia.data.model.FiltroModel
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.JuliaFiltroTabs
import com.unilever.julia.ui.filtro.activity.IFiltroActivity
import com.unilever.julia.ui.filtro.fragment.*
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.filtro_pedidos_activity.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import java.util.*
import javax.inject.Inject

class FiltroPedidoActivity : BaseViewActivity(), FiltroPedidoView, IFiltroActivity {

    @Inject
    lateinit var mPresenter: FiltroPedidoPresenter<FiltroPedidoView, FiltroPedidoInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filtro_pedidos_activity)

        tvToolbarTitle.text = getString(R.string.filtros)

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        tabs.setListener(object : JuliaFiltroTabs.Listener {
            override fun onTabClick(index: Int) {
                if (index == 0)
                    popBackStackFragment()
                else
                    loadFragment(FragmentType.ORDENACAO)
            }
        })

        contentButtons.setListener(object : ButtonsBottom.Listener {
            override fun onClickLeft() {
                mFiltroFragment?.onLimparFiltro()
            }

            override fun onClickRight() {
                mFiltroFragment?.onAplicarFiltro()
            }
        })

        val model = intent.getParcelableExtra<FiltroModel>(RedirectIntents.EXTRA_FILTRO)
        mPeriodo = model.periodo
        mValores = model.valores
        mOrdenacao = model.ordenacao

        loadFragment(FragmentType.MAIN)
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        popBackStackFragment()
    }

    enum class FragmentType {
        MAIN, PERIODO, VALORES, ORDENACAO
    }

    override fun loadFragment(fragmentType: FragmentType) {
        if (fragmentType == FragmentType.MAIN) {
            loadFragment(FiltroPedidoFragment())
        } else if (fragmentType == FragmentType.PERIODO) {
            setVisibleTabs(false)
            loadFragment(FiltroPeriodoFragment())
        } else if (fragmentType == FragmentType.VALORES) {
            setVisibleTabs(false)
            loadFragment(FiltroValoresFragment())
        } else if (fragmentType == FragmentType.ORDENACAO) {
            loadFragment(FiltroOrdenacaoFragment())
        }
    }

    private fun setVisibleTabs(visible: Boolean) {
        if (visible) {
            tabs.visibility = View.VISIBLE
        } else {
            tabs.visibility = View.GONE
        }
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment_content, fragment)
        transaction.addToBackStack(fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }

    override fun popBackStackFragment() {
        val stackEntryCount = supportFragmentManager.backStackEntryCount
        if (stackEntryCount <= 1) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        } else {
            setVisibleTabs(true)
            tabs.setActivite(0)
            supportFragmentManager.popBackStack()
        }
    }

    override fun setResultFinish() {
        val intent = Intent()
        intent.putExtra(RedirectIntents.EXTRA_FILTRO, FiltroModel(mPeriodo, mValores, mOrdenacao))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    var mFiltroFragment: IFiltroFragment? = null

    override fun setIFiltroFragment(filtroFragment: IFiltroFragment) {
        mFiltroFragment = filtroFragment
    }

    private var mPeriodo: Pair<Date, Date>? = null

    override fun setPeriodo(periodo: Pair<Date, Date>?) {
        mPeriodo = periodo
    }

    override fun getPeriodo(): Pair<Date, Date>? {
        return mPeriodo
    }

    private var mValores: Pair<Double, Double>? = null

    override fun setValores(periodo: Pair<Double, Double>?) {
        mValores = periodo
    }

    override fun getValores(): Pair<Double, Double>? {
        return mValores
    }

    private var mOrdenacao : FiltroOrdenacaoEnum? = null

    override fun setOrdenacao(ordenacao : FiltroOrdenacaoEnum?) {
        mOrdenacao = ordenacao
    }

    override fun getOrdenacao(): FiltroOrdenacaoEnum? {
        return mOrdenacao
    }
}