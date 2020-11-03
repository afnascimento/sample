package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseCode {

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("customer_id")
    @Expose
    var customerId: String? = null
}
