package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class GetLogoutRequest {
    @SerializedName("code")
    var code: String = ""
}