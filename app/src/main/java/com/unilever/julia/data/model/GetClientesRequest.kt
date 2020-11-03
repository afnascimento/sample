package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class GetClientesRequest {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("territory")
        var territory = ""

        constructor()

        constructor(territory: String) {
            this.territory = territory
        }
    }
}
