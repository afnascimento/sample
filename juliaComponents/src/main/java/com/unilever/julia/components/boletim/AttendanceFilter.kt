package com.unilever.julia.components.boletim

import android.os.Parcel
import android.os.Parcelable

data class AttendanceFilter(
    var territory: String = "",
    var district : String = "",
    var hitRegional : String = "",
    var subsidiary : String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(territory)
        parcel.writeString(district)
        parcel.writeString(hitRegional)
        parcel.writeString(subsidiary)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttendanceFilter> {
        override fun createFromParcel(parcel: Parcel): AttendanceFilter {
            return AttendanceFilter(parcel)
        }

        override fun newArray(size: Int): Array<AttendanceFilter?> {
            return arrayOfNulls(size)
        }
    }
}