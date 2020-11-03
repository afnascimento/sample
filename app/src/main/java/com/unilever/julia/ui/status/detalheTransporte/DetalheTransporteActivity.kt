package com.unilever.julia.ui.status.detalheTransporte

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unilever.julia.R
import com.unilever.julia.data.enums.StatusEnum
import com.unilever.julia.data.model.StatusPedidoItem
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.Utils
import kotlinx.android.synthetic.main.detalhe_transporte_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*

class DetalheTransporteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setFlagSecure(window)
        setContentView(R.layout.detalhe_transporte_activity)

        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        val status : StatusPedidoItem = intent.getParcelableExtra(RedirectIntents.EXTRA_STATUS_PEDIDO_ITEM)
        val tipo : StatusEnum = intent.getSerializableExtra(RedirectIntents.EXTRA_STATUS_ENUM) as StatusEnum

        juliaStatusDetCard.setItem(status, tipo)
    }
}