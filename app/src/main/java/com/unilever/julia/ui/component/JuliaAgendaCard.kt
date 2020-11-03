package com.unilever.julia.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.ButtonWrapContent
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.data.model.AgendaModel
import com.unilever.julia.utils.MaterialDialog
import org.apache.commons.lang3.StringUtils

class JuliaAgendaCard : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var btnDetail : ButtonWrapContent

    private var contentButtons : View

    private var btnOutlook : View

    private var btnCancelar : View

    private var btnConcluir : View

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_agenda_card, this)

        btnOutlook = findViewById(R.id.btnOutlook)

        contentButtons = findViewById(R.id.contentButtons)

        btnDetail = findViewById(R.id.btnDetail)

        btnDetail.setOnClickListener {
            mOnButtonClickListener?.onButtonDetailClick(mItem)
        }

        btnCancelar = findViewById(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            MaterialDialog(context)
                .setTitle(context.getString(R.string.agenda_cancelar_evento))
                .setNegativeButton(context.getString(R.string.dialog_nao))
                .setPositiveButton(context.getString(R.string.dialog_sim), MaterialDialog.OnClickListener {
                    mOnButtonClickListener?.onButtonCancelar(mItem)
                })
                .show()
        }

        btnConcluir = findViewById(R.id.btnConcluir)
        btnConcluir.setOnClickListener {
            MaterialDialog(context)
                .setTitle(context.getString(R.string.agenda_concluir_evento))
                .setNegativeButton(context.getString(R.string.dialog_nao))
                .setPositiveButton(context.getString(R.string.dialog_sim),  MaterialDialog.OnClickListener {
                    mOnButtonClickListener?.onButtonConcluir(mItem)
                })
                .show()
        }
    }

    private var mItem : AgendaModel.Item = AgendaModel.Item()

    fun addItem(it : AgendaModel.Item) {
        mItem = it

        if (it.isEventOutlook()) {
            btnOutlook.visibility = View.VISIBLE

            findViewById<JuliaTextViewIcon>(R.id.tvIcon2).visibility = View.GONE
            findViewById<TextView>(R.id.tvLocal).visibility = View.GONE

            contentButtons.visibility = View.GONE
        } else {
            btnOutlook.visibility = View.GONE

            findViewById<JuliaTextViewIcon>(R.id.tvIcon2).visibility = View.VISIBLE
            findViewById<TextView>(R.id.tvLocal).visibility = View.VISIBLE

            contentButtons.visibility = View.VISIBLE
        }

        findViewById<TextView>(R.id.tvData).text = StringUtils.trimToEmpty(it.activityDate)

        findViewById<TextView>(R.id.tvDescricao).text = StringUtils.trimToEmpty(it.description)

        findViewById<TextView>(R.id.tvLocal).text = StringUtils.trimToEmpty(it.subject)
    }

    private var mOnButtonClickListener : Listener? = null

    fun setListener(listener : Listener) {
        mOnButtonClickListener = listener
    }

    interface Listener {
        fun onButtonDetailClick(item : AgendaModel.Item)

        fun onButtonCancelar(item : AgendaModel.Item)

        fun onButtonConcluir(item : AgendaModel.Item)
    }

    fun setVisibleButton(visible: Boolean) {
        btnDetail.visibility = if (visible) View.VISIBLE else View.GONE
    }
}