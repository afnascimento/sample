package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {

    @SerializedName("accountId")
    @Expose
    var accountId : String = ""

    @SerializedName("givenName")
    @Expose
    var givenName : String = ""

    @SerializedName("surname")
    @Expose
    var surname : String = ""

    @SerializedName("mail")
    @Expose
    var mail : String = ""

    fun getUsername(): String {
        return "$givenName $surname"
    }
}