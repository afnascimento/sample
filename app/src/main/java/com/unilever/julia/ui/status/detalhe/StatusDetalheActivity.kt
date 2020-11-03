package com.unilever.julia.ui.status.detalhe

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.model.IStatusPedido
import com.unilever.julia.data.model.StatusPedidoItem
import com.unilever.julia.data.model.StatusPedidoModel
import com.unilever.julia.ui.adapter.StatusPedidoAdapter
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.julia_status_detalhe_activity.*
import kotlinx.android.synthetic.main.julia_status_detalhe_busca.*
import kotlinx.android.synthetic.main.julia_status_detalhe_content.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class StatusDetalheActivity : BaseViewActivity(), StatusDetalheView, StatusPedidoAdapter.Listener {

    @Inject lateinit var mPresenter : StatusDetalhePresenter<StatusDetalheView, StatusDetalheInteractor>

    lateinit var mAdapter : StatusPedidoAdapter

    lateinit var mStatusPedidoItem : StatusPedidoModel.Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.julia_status_detalhe_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        editTextBusca.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mPresenter.filterItems(s.toString())
            }
        })

        mAdapter = StatusPedidoAdapter(this@StatusDetalheActivity)
        rcviewStatus.adapter = mAdapter
        rcviewStatus.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this@StatusDetalheActivity, LinearLayout.VERTICAL, false)

        mStatusPedidoItem = intent.getParcelableExtra(RedirectIntents.EXTRA_STATUS)
        val sessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)

        mPresenter.sendStatusPedido(sessionCode, mStatusPedidoItem)
    }

    override fun getLoadContent(): View? {
        return loadContent
    }

    override fun getLoadView(): LoadView? {
        return juliaLoadView
    }

    override fun clearTextEditText() {
        editTextBusca.setText("", TextView.BufferType.EDITABLE)
    }

    override fun setItems(items: MutableList<IStatusPedido>) {
        mAdapter.addItems(items)
    }

    override fun onClickArrow(model: StatusPedidoItem) {
        RedirectIntents.redirectDetalheTransporteActivity(this@StatusDetalheActivity, model, mStatusPedidoItem.getStatusEnum())
    }
}