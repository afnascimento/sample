package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Conversations {

    @SerializedName("text")
    @Expose
    var text: String? = null

    @SerializedName("conversationChain")
    @Expose
    var sessionCode: String? = null

    @SerializedName("answers")
    @Expose
    var answers: MutableList<Answer>? = null

    @SerializedName("context")
    @Expose
    var context: Context? = null

    fun getAnswer(): Answer {
        return answers?.get(0) ?: Answer()
    }

    fun getEntity(): Entity? {
        if (!context?.entities.isNullOrEmpty())
            return context?.entities?.get(0)
        return null
    }

    fun getNextNode(): String {
        return context?.nextNode ?: ""
    }
}
