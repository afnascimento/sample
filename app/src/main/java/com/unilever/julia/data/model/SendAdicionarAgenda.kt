package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class SendAdicionarAgenda {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("gravar")
        var gravar = ""

        @SerializedName("data")
        var data = ""

        constructor()

        constructor(gravar: String, data: String) {
            this.gravar = gravar
            this.data = data
        }
    }
}
