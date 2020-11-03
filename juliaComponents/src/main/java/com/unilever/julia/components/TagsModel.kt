package com.unilever.julia.components

import android.os.Parcel
import android.os.Parcelable

class TagsModel(var code: String, var text: CharSequence) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TagsModel

        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        return code.hashCode()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(text.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TagsModel> {
        override fun createFromParcel(parcel: Parcel): TagsModel {
            return TagsModel(parcel)
        }

        override fun newArray(size: Int): Array<TagsModel?> {
            return arrayOfNulls(size)
        }
    }
}