package com.unilever.julia.ui.clientes

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unilever.julia.R
import com.unilever.julia.data.model.ClienteModel
import com.unilever.julia.data.model.PedidosClienteModel
import com.unilever.julia.ui.adapter.ClientesAdapter
import com.unilever.julia.ui.base.*
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.clientes_activity.*
import kotlinx.android.synthetic.main.clientes_content_sort.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class ClientesActivity : BaseViewActivity(), ClientesView, ClientesAdapter.Listener {

    @Inject
    lateinit var mPresenter: ClientesPresenter<ClientesView, ClientesInteractor>

    lateinit var mAdapter: ClientesAdapter

    lateinit var mSessionCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.clientes_activity)

        setSupportActionBar(toolbarBack)

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        editTextBusca.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mPresenter.filterItems(s.toString())
            }
        })

        rbGroupClientesSort.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.radioAsc) {
                mPresenter.sortItems(true)
            } else {
                mPresenter.sortItems(false)
            }
        }

        mAdapter = ClientesAdapter(this)
        rcviewClientes.setHasFixedSize(true)
        rcviewClientes.setItemViewCacheSize(20)
        rcviewClientes.layoutManager = LinearLayoutManager(this@ClientesActivity, RecyclerView.VERTICAL, false)
        rcviewClientes.adapter = mAdapter

        mSessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)
        val intention = intent.getStringExtra(RedirectIntents.EXTRA_INTENTION)
        val territory = intent.getStringExtra(RedirectIntents.EXTRA_TERRITORY)

        mPresenter.setTitleToolbar(territory)

        mPresenter.getClientes(mSessionCode, intention, territory)
    }

    override fun setTitle(title: String) {
        tvToolbarTitle.text = title
    }

    override fun onBackPressed() {
        setResult(RESULT_CANCELED)
        finish()
    }

    override fun showLoadView() {
        runOnUiThread {
            editTextBusca.visibility = View.GONE
            lineContentSort.visibility = View.GONE
            contentSort.visibility = View.GONE
            hideRecyclerViewItems()
            clientesLoadView.showLoading()
        }
    }

    override fun hideLoadView() {
        runOnUiThread {
            editTextBusca.visibility = View.VISIBLE
            lineContentSort.visibility = View.VISIBLE
            contentSort.visibility = View.VISIBLE
            showRecyclerViewItems()
            clientesLoadView.hide()
        }
    }

    override fun showRecyclerViewItems() {
        rcviewClientes.visibility = View.VISIBLE
    }

    override fun hideRecyclerViewItems() {
        rcviewClientes.visibility = View.GONE
    }

    override fun showContentLoadView() {
        clientesLoadView.visibility = View.VISIBLE
    }

    override fun hideContentLoadView() {
        clientesLoadView.visibility = View.GONE
    }

    override fun getLoadView(): LoadView? {
        return clientesLoadView
    }

    override fun setTextFilterInput(text: String) {
        editTextBusca.setText(text, TextView.BufferType.EDITABLE)
    }

    override fun addItems(items: MutableList<ClienteModel>) {
        mAdapter.addItems(items)
    }

    override fun onItemClick(item: ClienteModel) {
        val model = PedidosClienteModel("", item.code ?: "", item.customer?.code ?: "", mSessionCode)
        RedirectIntents.redirectPedidosClienteActivity(this, model, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data)
            finish()
        }
    }
}