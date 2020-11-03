package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

data class Enviroment(
    @SerializedName("broker")
    var broker : Broker = Broker(),
    @SerializedName("certificate")
    var certificate : Certificate = Certificate()
) {

    data class Certificate(
        @SerializedName("key")
        var key : String = ""
    )

    data class Broker(
        @SerializedName("project")
        var project : String = "",
        @SerializedName("channel")
        var channel : String = "",
        @SerializedName("os")
        var os : String = "",
        @SerializedName("url")
        var url : String = "",
        @SerializedName("apiKey")
        var apiKey : String = "",
        @SerializedName("accessControlAllowOrigin")
        var accessControlAllowOrigin : String = ""
    )
}