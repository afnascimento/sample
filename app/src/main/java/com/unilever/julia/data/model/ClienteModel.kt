package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class ClienteModel {

    @SerializedName("code")
    var code : String? = null

    @SerializedName("customer")
    var customer : Customer? = null

    fun getPercentSort(): Double {
        return customer?.value ?: 0.0
    }

    class Customer {

        @SerializedName("value")
        var value : Double? = null

        @SerializedName("color")
        var color : String? = null

        @SerializedName("code")
        var code : String? = null

        @SerializedName("name")
        var name : String? = null

        @SerializedName("state")
        var state : String? = null

        @SerializedName("city")
        var city : String? = null

        @SerializedName("district")
        var district : String? = null

        @SerializedName("street")
        var street : String? = null

        fun getAddress(): String {
            val mState : String = state ?: ""
            val mCity : String = city ?: ""
            val mDistrict : String = district ?: ""
            //val mStreet : String = street ?: ""
            return "$mCity - $mState - $mDistrict"
        }

        fun getNameWithCode(): String {
            val codeString : String = code ?: ""
            val nameString : String = name ?: ""
            return  "$codeString - $nameString"
        }

        fun getTextFilter(): String {
            return getNameWithCode() +" "+ getAddress()
        }
    }
}