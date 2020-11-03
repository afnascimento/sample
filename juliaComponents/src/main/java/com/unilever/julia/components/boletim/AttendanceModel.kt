package com.unilever.julia.components.boletim

import android.os.Parcel
import android.os.Parcelable

data class AttendanceModel(
    var id : String = "",
    var description : String = "",
    var textFilter : String = "",
    var textSorted : String = "",
    var type: Type = Type.territory
) : Parcelable {

    enum class Type {
        territory,
        district,
        hitRegional,
        subsidiary
    }

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        Type.values()[parcel.readInt()]
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(description)
        parcel.writeString(textFilter)
        parcel.writeString(textSorted)
        parcel.writeInt(type.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttendanceModel> {
        override fun createFromParcel(parcel: Parcel): AttendanceModel {
            return AttendanceModel(parcel)
        }

        override fun newArray(size: Int): Array<AttendanceModel?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AttendanceModel

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}