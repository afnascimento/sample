package com.unilever.julia.ui.agenda

import android.app.Activity
import android.os.Bundle
import com.unilever.julia.R
import com.unilever.julia.data.enums.AgendaStatusEnum
import com.unilever.julia.data.model.AgendaModel
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.ui.component.JuliaAgendaCard
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.agenda_activity.*
import kotlinx.android.synthetic.main.toolbar_back.*
import kotlinx.android.synthetic.main.toolbar_back_content.*
import javax.inject.Inject

class AgendaActivity : BaseViewActivity(), AgendaView {

    @Inject lateinit var mPresenter : AgendaPresenter<AgendaView, AgendaInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agenda_activity)
        setSupportActionBar(toolbarBack)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = ""

        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        val sessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE)
        val model : AgendaModel.Item = intent.getParcelableExtra(RedirectIntents.EXTRA_AGENDA)

        agendaDetalhe.addItem(model)
        agendaDetalhe.setVisibleButton(false)
        agendaDetalhe.setListener(object : JuliaAgendaCard.Listener {
            override fun onButtonDetailClick(item: AgendaModel.Item) {

            }

            override fun onButtonCancelar(item: AgendaModel.Item) {
                mPresenter.agendaMudarStatusEvento(sessionCode, AgendaStatusEnum.CANCELAR, model)
            }

            override fun onButtonConcluir(item: AgendaModel.Item) {
                mPresenter.agendaMudarStatusEvento(sessionCode, AgendaStatusEnum.CONCLUIR, model)
            }
        })
    }

    override fun eventoFinalizado() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}