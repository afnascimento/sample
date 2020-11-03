package com.unilever.julia.firebase.parser

import android.os.Parcel
import android.os.Parcelable

class ActionTextNotification : ActionBaseNotification {

    var mTextChat: String = ""

    override fun getType(): FirebaseNotification.Type {
        return FirebaseNotification.Type.TEXT
    }

    constructor(
        mTitle: String,
        mBody: String,
        mColor: String,
        mIcon: String,
        mParam: String,
        mId: String,
        mTextChat: String
    ) : super(mTitle, mBody, mColor, mIcon, mParam, mId) {
        this.mTextChat = mTextChat
    }

    constructor(parcel: Parcel) : super(parcel) {
        this.mTextChat = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeString(mTextChat)
    }

    companion object CREATOR : Parcelable.Creator<ActionTextNotification> {
        override fun createFromParcel(parcel: Parcel): ActionTextNotification {
            return ActionTextNotification(parcel)
        }

        override fun newArray(size: Int): Array<ActionTextNotification?> {
            return arrayOfNulls(size)
        }
    }
}