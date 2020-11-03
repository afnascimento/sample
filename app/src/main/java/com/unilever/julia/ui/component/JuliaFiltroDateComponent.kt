package com.unilever.julia.ui.component

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.unilever.julia.R
import com.unilever.julia.components.JuliaTextViewIcon
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils
import java.util.*

class JuliaFiltroDateComponent : ConstraintLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var textInputDate : TextInputLayout

    private var editDate : EditText

    private var tvTitle : TextView

    private var mType = TypeEnums.DATE

    enum class TypeEnums {
        DATE, TIME
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        var title = context.getString(R.string.de)

        var iconVisible = false

        var iconEnum = IconEnums.CALENDARIO

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.JuliaFiltroDateComponent, 0, 0
            )
            try {
                val tempTitle = typedArray.getString(R.styleable.JuliaFiltroDateComponent_julFilDtTitle)
                if (StringUtils.isNotBlank(tempTitle)) {
                    title = StringUtils.trimToEmpty(tempTitle)
                }

                iconVisible = typedArray.getBoolean(R.styleable.JuliaFiltroDateComponent_julFilDtIconVisible, iconVisible)

                val julTextViewIcon = typedArray.getInt(R.styleable.JuliaFiltroDateComponent_julTextViewIcon, -1)
                if (julTextViewIcon >= 0) {
                    iconEnum = IconEnums.values()[julTextViewIcon]
                }

                val type = typedArray.getInt(R.styleable.JuliaFiltroDateComponent_julDateType, 0)
                mType = TypeEnums.values()[type]
            } finally {
                typedArray.recycle()
            }
        }

        inflate(context, R.layout.julia_filter_date, this)

        tvTitle = findViewById(R.id.tvTitle)
        tvTitle.text = title

        if (iconVisible) {
            val tvIcon = findViewById<JuliaTextViewIcon>(R.id.tvIcon)
            tvIcon.setIcon(iconEnum.iconHexa)
            tvIcon.visibility = View.VISIBLE

            editDate = LayoutInflater.from(context).inflate(R.layout.julia_filter_date_input2, null, false) as EditText
        } else {
            editDate = LayoutInflater.from(context).inflate(R.layout.julia_filter_date_input, null, false) as EditText
        }
        editDate.setOnClickListener {
            openDialogDate()
        }

        textInputDate = findViewById(R.id.textInputDate)
        textInputDate.addView(editDate)
    }

    private fun openDialogDate() {
        if (mType == TypeEnums.DATE) {
            val c = Calendar.getInstance()
            c.time = getDate()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(context, mListenerDate, year, month, day).show()
        } else {
            val time = getTime()
            val hour = time.first
            val minute = time.second
            TimePickerDialog(context, mListenerTime, hour, minute, true).show()
        }
    }

    private val mListenerTime = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        setTime(hourOfDay, minute)
    }

    private val mListenerDate = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        val vDay = view.dayOfMonth
        val vMonth = view.month + 1
        val vYear = view.year
        val date : Date = Utils.toDate("$vDay/$vMonth/$vYear", "dd/MM/yyyy") ?: Date()
        setDate(date)
    }

    fun setTitle(title: String) {
        tvTitle.text = title
    }

    fun setEnabledEdit(enabled : Boolean) {
        editDate.isEnabled = enabled
    }

    private val PATTERN = "dd/MM/yyyy"

    fun setDate(date: Date) {
        val value = Utils.toStringByDate(Utils.removeTime(date), PATTERN)
        editDate.setText(value, TextView.BufferType.EDITABLE)
    }

    fun getDate(): Date {
        val value = editDate.text.toString()
        if (StringUtils.isEmpty(value))
            return Utils.removeTime(Date())
        else
            return Utils.toDate(value, PATTERN) ?: Utils.removeTime(Date())
    }

    fun setTime(hour: Int, minute: Int) {
        val hourOfDay = StringUtils.leftPad(hour.toString(), 2,"0")
        val minuteOfDay = StringUtils.leftPad(minute.toString(), 2,"0")
        val value = "$hourOfDay:$minuteOfDay"
        editDate.setText(value, TextView.BufferType.EDITABLE)
    }

    fun setValue(value : String) {
        editDate.setText(value, TextView.BufferType.EDITABLE)
    }

    fun getValueString(): String {
        return editDate.text.toString()
    }

    fun getTime(): Pair<Int, Int> {
        val value = editDate.text.toString()
        if (StringUtils.isEmpty(value)) {
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            return Pair(hour, minute)
        } else {
            val split = StringUtils.split(value, ":")
            val hour = split[0].toInt()
            val minute = split[1].toInt()
            return Pair(hour, minute)
        }
    }

    fun setError(error: String) {
        textInputDate.error = error
    }

    fun clearError() {
        textInputDate.error = null
    }
}