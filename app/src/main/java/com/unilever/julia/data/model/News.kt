package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("news")
    var items : MutableList<Item> = arrayListOf()
) {
    data class Item(
        @SerializedName("title")
        var title : String = "",
        @SerializedName("description")
        var description : String = "",
        @SerializedName("image")
        var image : String = ""
    )
}