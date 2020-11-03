package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Answer {

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("technicalText")
    @Expose
    var technicalText: String? = null

    @SerializedName("options")
    @Expose
    var options: MutableList<Option>? = null

    constructor()

    constructor(title: String?, text: String?) {
        this.title = title
        this.text = text
    }

    fun isSuccess(): Boolean {
        return technicalText?.equals("success", true) ?: false
    }

    fun title(): String {
        return title ?: ""
    }

    fun options(): MutableList<Option> {
        return options ?: arrayListOf()
    }
}
