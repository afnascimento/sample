package com.unilever.julia.data.model.inovacaoDetalhe

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.unilever.julia.components.innovations.IProduct

data class Produto(
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("description")
    var description: String? = "",
    @SerializedName("imagen")
    var image: String? = "",
    @Transient
    var isErrorRenderer: Boolean = false
) : Parcelable, IProduct {

    override fun getProductTitle(): String {
        return title ?: ""
    }

    override fun getProductDescription(): String {
        return description ?: ""
    }

    override fun getProductImage(): String {
        return image ?: ""
    }

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Produto> {
        override fun createFromParcel(parcel: Parcel): Produto {
            return Produto(parcel)
        }

        override fun newArray(size: Int): Array<Produto?> {
            return arrayOfNulls(size)
        }
    }
}