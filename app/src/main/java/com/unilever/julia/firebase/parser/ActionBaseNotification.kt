package com.unilever.julia.firebase.parser

import android.content.Context
import android.content.Intent
import android.os.Parcel
import com.unilever.julia.ui.splash.SplashActivity
import com.unilever.julia.utils.RedirectIntents

abstract class ActionBaseNotification : FirebaseNotification {
    var mTitle: String = ""
    var mBody: String = ""
    var mColor: String = ""
    var mIcon: String = ""
    var mParam: String = ""
    var mId: String = ""

    override fun getIntent(context: Context): Intent {
        val intent = Intent(context, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(RedirectIntents.EXTRA_NOTIFICATION, this)
        return intent
    }

    override fun getTitle(): String {
        return mTitle
    }

    override fun getBody(): String {
        return mBody
    }

    override fun getColor(): String {
        return mColor
    }

    override fun getIcon(): String {
        return mIcon
    }

    override fun getParam(): String {
        return mParam
    }

    override fun getId(): String {
        return mId
    }

    constructor(
        mTitle: String,
        mBody: String,
        mColor: String,
        mIcon: String,
        mParam: String,
        mId: String
    ) {
        this.mTitle = mTitle
        this.mBody = mBody
        this.mColor = mColor
        this.mIcon = mIcon
        this.mParam = mParam
        this.mId = mId
    }

    constructor(parcel: Parcel) {
        this.mTitle = parcel.readString() ?: ""
        this.mBody = parcel.readString() ?: ""
        this.mColor = parcel.readString() ?: ""
        this.mIcon = parcel.readString() ?: ""
        this.mParam = parcel.readString() ?: ""
        this.mId = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mTitle)
        parcel.writeString(mBody)
        parcel.writeString(mColor)
        parcel.writeString(mIcon)
        parcel.writeString(mParam)
        parcel.writeString(mId)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActionBaseNotification

        if (mTitle != other.mTitle) return false
        if (mBody != other.mBody) return false
        if (mColor != other.mColor) return false
        if (mIcon != other.mIcon) return false
        if (mParam != other.mParam) return false
        if (mId != other.mId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mTitle.hashCode()
        result = 31 * result + mBody.hashCode()
        result = 31 * result + mColor.hashCode()
        result = 31 * result + mIcon.hashCode()
        result = 31 * result + mParam.hashCode()
        result = 31 * result + mId.hashCode()
        return result
    }
}