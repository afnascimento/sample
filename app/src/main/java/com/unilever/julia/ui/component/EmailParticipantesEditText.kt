package com.unilever.julia.ui.component

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.utils.Utils

class EmailParticipantesEditText : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var contentEdittext : View

    private var btnRemove : View

    private var editEmail : EditText

    private var textRequired : TextView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.email_participantes_edittext, this)

        contentEdittext = findViewById(R.id.contentEdittext)

        btnRemove = findViewById(R.id.btnRemove)
        btnRemove.setOnClickListener {
            mListener?.onRemove(this@EmailParticipantesEditText)
        }

        editEmail = findViewById(R.id.editEmail)

        textRequired = findViewById(R.id.textRequired)
    }

    interface Listener {
        fun onRemove(view: View)
    }

    private var mListener : Listener? = null

    fun setListener(listener : Listener) {
        this.mListener = listener
    }

    fun getValue(): String {
        return editEmail.text.toString()
    }

    fun setValue(value: String) {
        editEmail.setText(value, TextView.BufferType.EDITABLE)
    }

    fun setError(text: String?) {
        if (text == null) {
            contentEdittext.background = ContextCompat.getDrawable(context, R.drawable.bg_email_edittext)
            textRequired.visibility = View.GONE
        } else {
            contentEdittext.background = ContextCompat.getDrawable(context, R.drawable.bg_email_edittext_error)
            textRequired.text = text
            textRequired.visibility = View.VISIBLE
        }
    }

    fun showKeyboard() {
        editEmail.requestFocus()
        editEmail.postDelayed({ Utils.showKeyboard(context, editEmail) }, 100)
    }
}