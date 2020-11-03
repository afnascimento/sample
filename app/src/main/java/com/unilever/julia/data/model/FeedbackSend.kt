package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class FeedbackSend {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("text")
        var text = ""

        constructor()

        constructor(text: String) {
            this.text = text
        }
    }
}
