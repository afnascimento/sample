package com.unilever.julia.firebase.parser

import android.os.Parcel
import android.os.Parcelable

class ActionNoneNotification : ActionBaseNotification {

    override fun getType(): FirebaseNotification.Type {
        return FirebaseNotification.Type.NONE
    }

    constructor(
        mTitle: String,
        mBody: String,
        mColor: String,
        mIcon: String,
        mParam: String,
        mId: String
    ) : super(mTitle, mBody, mColor, mIcon, mParam, mId)

    constructor(parcel: Parcel) : super(parcel)

    companion object CREATOR : Parcelable.Creator<ActionNoneNotification> {
        override fun createFromParcel(parcel: Parcel): ActionNoneNotification {
            return ActionNoneNotification(parcel)
        }

        override fun newArray(size: Int): Array<ActionNoneNotification?> {
            return arrayOfNulls(size)
        }
    }
}