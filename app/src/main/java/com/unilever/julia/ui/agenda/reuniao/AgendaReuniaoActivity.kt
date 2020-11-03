package com.unilever.julia.ui.agenda.reuniao

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonsBottom
import com.unilever.julia.data.model.AgendaOutlookContext
import com.unilever.julia.data.model.AgendaOutlookParticipante
import com.unilever.julia.data.model.AgendaOutlookSucesso
import com.unilever.julia.ui.base.BaseViewActivity
import com.unilever.julia.ui.component.JuliaFiltroDateComponent
import com.unilever.julia.utils.MaterialDialog
import com.unilever.julia.utils.RedirectIntents
import kotlinx.android.synthetic.main.agenda_reuniao_content.*
import kotlinx.android.synthetic.main.agenda_reuniao_local.*
import kotlinx.android.synthetic.main.agenda_reuniao_title.*
import kotlinx.android.synthetic.main.agenda_reuniao_toolbar.*
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class AgendaReuniaoActivity : BaseViewActivity(), AgendaReuniaoView {

    @Inject
    lateinit var mPresenter : AgendaReuniaoPresenter<AgendaReuniaoView, AgendaReuniaoInteractor>

    var mSessionCode : String = ""

    var mAgenda : AgendaOutlookContext? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.agenda_reuniao_activity)

        setSupportActionBar(toolbarAgendaReuniao)
        toolbarBackArrow.setOnClickListener {
            onBackPressed()
        }

        mSessionCode = intent.getStringExtra(RedirectIntents.EXTRA_SESSION_CODE) ?: ""

        mAgenda = intent.getParcelableExtra(RedirectIntents.EXTRA_AGENDA_OUTLOOK_CONTEXT)

        val owner : Boolean = mAgenda?.owner ?: false

        if (mAgenda == null) {
            tvToolbarTitle.text = getString(R.string.agenda_reuniao_toolbar_title_adicionar)
            buttonsBottom.setTextButton(ButtonsBottom.Direction.LEFT, getString(R.string.cancelar))
        } else {
            tvToolbarTitle.text = getString(R.string.agenda_reunicao_toolbar_title_edicao)
            buttonsBottom.setTextButton(ButtonsBottom.Direction.LEFT, getString(R.string.excluir))

            editTitleReuniao.setText(StringUtils.trimToEmpty(mAgenda?.assunto), TextView.BufferType.EDITABLE)

            dtInicio.setValue(StringUtils.trimToEmpty(mAgenda?.dataInicio))
            dtFim.setValue(StringUtils.trimToEmpty(mAgenda?.dataFim))

            hrInicio.setValue(StringUtils.trimToEmpty(mAgenda?.horaInicio))
            hrFim.setValue(StringUtils.trimToEmpty(mAgenda?.horaFim))

            editLocalReuniao.setText(StringUtils.trimToEmpty(mAgenda?.local), TextView.BufferType.EDITABLE)

            val lista = arrayListOf<AgendaOutlookParticipante>()
            if (!mAgenda?.participantes.isNullOrEmpty()) {
                lista.addAll(mAgenda?.participantes!!)
            }

            if (owner) {
                for (it in lista) {
                    emailParticipantes.addEditText(StringUtils.trimToEmpty(it.email), false)
                }
            } else {
                tvToolbarTitle.text = getString(R.string.agenda_reuniao_toolbar_title_detalhe)

                editTitleReuniao.isEnabled = false
                dtInicio.setEnabledEdit(false)
                dtFim.setEnabledEdit(false)
                hrInicio.setEnabledEdit(false)
                hrFim.setEnabledEdit(false)
                editLocalReuniao.isEnabled = false

                emailParticipantes.setVisibleButtonAdd(false)
                for (it in lista) {
                    emailParticipantes.addTextView(StringUtils.trimToEmpty(it.email))
                }

                buttonsBottom.setVisibilityButton(ButtonsBottom.Direction.LEFT, false)
                buttonsBottom.setVisibilityButton(ButtonsBottom.Direction.RIGHT, false)
            }
        }

        buttonsBottom.setListener(object : ButtonsBottom.Listener {
            override fun onClickLeft() {
                mPresenter.onClickButtonCancelar(owner)
            }

            override fun onClickRight() {
                mPresenter.enviarGravacaoAgenda(
                    mAgenda?.id,
                    editTitleReuniao.text.toString(),
                    dtInicio.getValueString(),
                    dtFim.getValueString(),
                    hrInicio.getValueString(),
                    hrFim.getValueString(),
                    editLocalReuniao.text.toString(),
                    emailParticipantes.getEmails(),
                    mSessionCode)
            }
        })
    }

    override fun onExcluirDialog() {
        MaterialDialog(this)
            .setTitle(getString(R.string.agenda_excluir_evento))
            .setNegativeButton(getString(R.string.dialog_nao))
            .setPositiveButton(getString(R.string.dialog_sim), MaterialDialog.OnClickListener {
                mPresenter.enviarExclusaoAgenda(mAgenda?.id ?: "", mSessionCode)
            })
            .show()
    }

    override fun onCancelar() {
        onBackPressed()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    override fun agendaGravadaSucesso(agenda: AgendaOutlookSucesso) {
        val intent = Intent()
        intent.putExtra(RedirectIntents.EXTRA_AGENDA_OUTLOOK_SUCESSO, agenda)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun agendaExcluidaSucesso(text: String) {
        val intent = Intent()
        intent.putExtra(RedirectIntents.EXTRA_AGENDA_OUTLOOK_EXCLUSAO, text)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun setErrorTituloReuniao(error: String?) {
        textInputTitleReuniao.error = error
    }

    override fun setErrorLocal(error: String?) {
        textInputLocal.error = error
    }

    override fun setErrorDataInicio(error: String?) {
        setError(dtInicio, error)
    }

    override fun setErrorDataFim(error: String?) {
        setError(dtFim, error)
    }

    override fun setErrorHorarioInicio(error: String?) {
        setError(hrInicio, error)
    }

    override fun setErrorHorarioFim(error: String?) {
        setError(hrFim, error)
    }

    private fun setError(component: JuliaFiltroDateComponent, error: String?) {
        if (error == null) {
            component.clearError()
        } else {
            component.setError(error)
        }
    }

    override fun setErrorParticipante(index: Int, textError: String?) {
        emailParticipantes.setError(index, textError)
    }
}