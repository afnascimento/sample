package com.unilever.julia.ui.component

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.data.model.EmailParticipante

class EmailParticipantesComponent : ConstraintLayout, EmailParticipantesEditText.Listener {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var btnAddEmail : View

    private var contentEmails : LinearLayout

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.email_participantes_layout, this)

        btnAddEmail = findViewById(R.id.btnAddEmail)
        btnAddEmail.setOnClickListener {
            addEditText("", true)
        }

        contentEmails = findViewById(R.id.contentEmails)
    }

    private var mEmailParticipantesEditText : MutableList<EmailParticipantesEditText> = arrayListOf()

    fun setVisibleButtonAdd(visible : Boolean) {
        if (visible) {
            btnAddEmail.visibility = View.VISIBLE
        } else {
            btnAddEmail.visibility = View.GONE
        }
    }

    fun addTextView(value: String) {
        val tvEmail : TextView = LayoutInflater.from(context).inflate(R.layout.email_participantes_textview, contentEmails, false) as TextView
        tvEmail.text = value
        contentEmails.addView(tvEmail)
    }

    fun addEditText(value: String, focus: Boolean) {
        val editText = EmailParticipantesEditText(context)
        editText.setListener(this@EmailParticipantesComponent)
        editText.setValue(value)
        if (focus) {
            editText.showKeyboard()
        }
        mEmailParticipantesEditText.add(editText)
        contentEmails.addView(editText)
    }

    override fun onRemove(view: View) {
        mEmailParticipantesEditText.remove(view)
        contentEmails.removeView(view)
    }

    fun setError(index: Int, textError: String?) {
        val edit = mEmailParticipantesEditText[index]
        edit.setError(textError)
    }

    fun getEmails(): MutableList<EmailParticipante>? {

        if (mEmailParticipantesEditText.isNotEmpty()) {
            val list = arrayListOf<EmailParticipante>()

            for (edit in mEmailParticipantesEditText) {
                list.add(EmailParticipante(mEmailParticipantesEditText.indexOf(edit), edit.getValue()))
            }

            return list
        }

        return null
    }

    /*
    private fun getEmailParticipantesEditText(): MutableList<EmailParticipantesEditText> {
        val list = arrayListOf<EmailParticipantesEditText>()

        for (i in 0..contentEmails.childCount) {
            val view = contentEmails.getChildAt(i)
            if (view is EmailParticipantesEditText) {
                list.add(view)
            }
        }

        return list
    }
    */
}