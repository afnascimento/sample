package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class CloseFlowSend {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("event")
        var event = ""

        @SerializedName("param")
        var param = ""

        constructor()

        constructor(event: String, param: String) {
            this.event = event
            this.param = param
        }
    }
}
