package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GraphProfile {

    @SerializedName("givenName")
    @Expose
    var givenName : String? = null

    @SerializedName("surname")
    @Expose
    var surname : String? = null

    @SerializedName("mail")
    @Expose
    var mail : String? = null
}