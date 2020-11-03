package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

data class ErrorApiException(
    @SerializedName("error")
    var error: Error? = Error()
) {
    data class Error(
        @SerializedName("icon")
        var icon: String? = "",
        @SerializedName("message")
        var message: String? = "",
        @SerializedName("title")
        var title: String? = ""
    )
}