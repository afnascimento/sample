package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Intent {

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("confidence")
    @Expose
    var confidence: Double? = null

}
