package com.unilever.julia.components.innovations

import android.widget.TextView
import org.apache.commons.lang3.StringUtils

class InnovationUtils {
    companion object {
        fun setDescription(textView: TextView, description: String) {
            val split = StringUtils.split(description, ";")
            if (split.isNullOrEmpty()) {
                textView.text = description
            } else {
                val builder = StringBuilder()
                for (it in split) {
                    builder.append(StringUtils.trim(it))
                    builder.append("\n")
                }
                val text = StringUtils.removeEnd(builder.toString(), "\n")
                textView.text = text
            }
        }
    }
}