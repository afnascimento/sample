package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Option {

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("action")
    @Expose
    var action: Boolean? = null

}
