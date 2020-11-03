package com.unilever.julia.data.enums

import android.content.Context
import com.unilever.julia.R
import org.apache.commons.lang3.StringUtils

enum class StatusEnum(var textRes: Int) {
    FATURADO(R.string.faturado),
    ANULADO(R.string.anulado),
    PENDENTE(R.string.pendente);

    fun getText(context: Context): String {
        return context.getString(textRes)
    }

    companion object {
        fun getStatusEnum(status: String): StatusEnum {
            if (StringUtils.equalsIgnoreCase(status, "FACT")) {
                return FATURADO
            } else if (StringUtils.equalsIgnoreCase(status, "ANUL")) {
                return ANULADO
            }
            return PENDENTE
        }
    }
}