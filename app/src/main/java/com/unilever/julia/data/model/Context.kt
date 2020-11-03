package com.unilever.julia.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Context {

    @SerializedName("previous_node")
    @Expose
    var previousNode: String? = null

    @SerializedName("intents")
    @Expose
    var intents: MutableList<Intent>? = null

    @SerializedName("next_node")
    @Expose
    var nextNode: String? = null

    @SerializedName("entities")
    @Expose
    var entities: MutableList<Entity>? = null

}
