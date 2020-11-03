package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class AgendaOutlookExcluir {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {
        @SerializedName("id")
        var id = ""
    }
}