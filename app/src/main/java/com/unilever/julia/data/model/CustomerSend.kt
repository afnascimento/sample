package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class CustomerSend {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("customer_id")
        var customerId = ""

        constructor()

        constructor(customerId: String) {
            this.customerId = customerId
        }
    }
}
