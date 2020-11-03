package com.unilever.julia.firebase.parser

import android.os.Parcel
import android.os.Parcelable

class ActionCodeNotification : ActionBaseNotification {

    var mIntent: String = ""

    override fun getType(): FirebaseNotification.Type {
        return FirebaseNotification.Type.CODE
    }

    constructor(
        mTitle: String,
        mBody: String,
        mColor: String,
        mIcon: String,
        mParam: String,
        mId: String,
        mIntent: String
    ) : super(mTitle, mBody, mColor, mIcon, mParam, mId) {
        this.mIntent = mIntent
    }

    constructor(parcel: Parcel) : super(parcel) {
        this.mIntent = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(mIntent)
    }

    companion object CREATOR : Parcelable.Creator<ActionCodeNotification> {
        override fun createFromParcel(parcel: Parcel): ActionCodeNotification {
            return ActionCodeNotification(parcel)
        }

        override fun newArray(size: Int): Array<ActionCodeNotification?> {
            return arrayOfNulls(size)
        }
    }
}