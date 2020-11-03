package com.unilever.julia.ui.pedidos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.FiltroModel
import com.unilever.julia.data.model.PedidoClienteResponse
import com.unilever.julia.data.model.PedidosClienteModel
import com.unilever.julia.ui.adapter.PedidosClienteAdapter
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.pedidos_cliente_activity.*
import kotlinx.android.synthetic.main.pedidos_cliente_toolbar.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class PedidosClienteActivity : BaseViewActivity(), PedidosClienteView, PedidosClienteAdapter.Listener {

    @Inject lateinit var mPresenter : PedidosClientePresenter<PedidosClienteView, PedidosClienteInteractor>

    lateinit var mAdapter: PedidosClienteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pedidos_cliente_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        imgFiltro.visibility = View.VISIBLE
        imgFiltro.setOnClickListener {
            mPresenter.redirectFiltroPedido()
        }

        tvToolbarTitle.text = getString(R.string.pedidos_do_cliente)

        mAdapter = PedidosClienteAdapter(this@PedidosClienteActivity)
        rcview.adapter = mAdapter
        rcview.layoutManager = LinearLayoutManager(this@PedidosClienteActivity, RecyclerView.VERTICAL, false)

        val model : PedidosClienteModel = intent.getParcelableExtra(RedirectIntents.EXTRA_PEDIDO_CLIENTE)
        mPresenter.getPedidosCliente(model)
    }

    override fun redirectFiltroPedidoActivity(filtroModel: FiltroModel) {
        RedirectIntents.redirectFiltroPedidoActivity(this@PedidosClienteActivity, filtroModel, REQUEST_CODE_FILTRO)
    }

    private val REQUEST_CODE_FILTRO = 1000

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_FILTRO) {
            if (data != null) {
                mPresenter.filtrarDados(data.getParcelableExtra(RedirectIntents.EXTRA_FILTRO))
            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun getLoadContent(): View? {
        return rcview
    }

    override fun getLoadView(): LoadView? {
        return juliaLoadView
    }

    override fun addItems(items: MutableList<PedidoClienteResponse>) {
        mAdapter.addItems(items)
    }

    override fun onItemClick(item: PedidoClienteResponse) {
        val intent = Intent()
        intent.putExtra(RedirectIntents.EXTRA_PEDIDO_CLIENTE, item)
        setResult(RESULT_OK, intent)
        finish()
    }
}