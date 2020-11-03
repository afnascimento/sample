package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class AgendaOutlookGravacao {

    @SerializedName("code")
    var code: String? = null

    @SerializedName("context")
    var context: AgendaOutlookContext? = null
}