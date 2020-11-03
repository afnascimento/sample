package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.JuliaAnswerComponent
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.data.model.AgendaOutlookParticipante
import com.unilever.julia.data.model.AgendaOutlookSucesso
import org.apache.commons.lang3.StringUtils

class JuliaCardAgendaEdicaoChat : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var juliaAnswer : JuliaAnswerComponent

    private var tvTitulo : TextView

    private var tvData : TextView

    private var tvHorario : TextView

    private var iconParticipantes : JuliaTextViewIcon

    private var tvParticipantes : TextView

    private var tvLocal : TextView

    private var btnEdit : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.card_edicao_agenda_chat, this)

        juliaAnswer = findViewById(R.id.julAnswerCard)

        tvTitulo = findViewById(R.id.tvTitulo)

        tvData = findViewById(R.id.tvData)

        tvHorario = findViewById(R.id.tvHorario)

        iconParticipantes = findViewById(R.id.iconParticipantes)

        tvParticipantes = findViewById(R.id.tvParticipantes)

        tvLocal = findViewById(R.id.tvLocal)

        btnEdit = findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener {
            if (mAgenda != null) {
                mListener?.onEdit(mAgenda!!)
            }
        }
    }

    private var mAgenda : AgendaOutlookSucesso? = null

    fun setAgendaOutlook(agenda : AgendaOutlookSucesso) {
        mAgenda = agenda

        juliaAnswer.setText(StringUtils.trimToEmpty(agenda.text))
        tvTitulo.text = StringUtils.trimToEmpty(agenda.context?.assunto)
        tvData.text = context.getString(R.string.agenda_edicao_datas,
            StringUtils.trimToEmpty(agenda.context?.dataInicio), StringUtils.trimToEmpty(agenda.context?.dataFim))
        tvHorario.text = context.getString(R.string.agenda_edicao_horario,
            StringUtils.trimToEmpty(agenda.context?.horaInicio), StringUtils.trimToEmpty(agenda.context?.horaFim))

        if (agenda.context?.participantes.isNullOrEmpty()) {
            iconParticipantes.visibility = View.GONE
            tvParticipantes.visibility = View.GONE
        } else {
            iconParticipantes.visibility = View.VISIBLE
            tvParticipantes.visibility = View.VISIBLE
            val participante : AgendaOutlookParticipante? = agenda.context?.participantes?.get(0)

            val size = agenda.context?.participantes?.size ?: 1
            if (size > 1) {
                tvParticipantes.text = context.getString(R.string.agenda_participantes, StringUtils.trimToEmpty(participante?.email), size)
            } else {
                tvParticipantes.text = StringUtils.trimToEmpty(participante?.email)
            }
        }

        tvLocal.text = StringUtils.trimToEmpty(agenda.context?.local)
    }

    interface Listener {
        fun onEdit(agenda : AgendaOutlookSucesso)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        mListener = listener
    }
}