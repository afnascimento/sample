package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class CustomerModel {

    @SerializedName("id")
    var id : String? = null

    @SerializedName("territory")
    var territory : String? = null

    @SerializedName("name")
    var name : String? = null

    @SerializedName("state")
    var state : String? = null

    @SerializedName("city")
    var city : String? = null

    @SerializedName("district")
    var district : String? = null
}