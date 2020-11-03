package com.unilever.julia.data.model.inovacaoDetalhe

import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.StringUtils

data class TitleAndDescription(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("description")
    var description: String? = ""
) {
    fun isValid(): Boolean {
        return !StringUtils.isAnyEmpty(title, description)
    }
}