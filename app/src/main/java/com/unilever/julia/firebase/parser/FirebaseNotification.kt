package com.unilever.julia.firebase.parser

import android.content.Context
import android.content.Intent
import android.os.Parcelable

interface FirebaseNotification : Parcelable {

    enum class Type(val key : String) {
        TEXT("text"),
        NONE("none"),
        CODE("code");

        companion object {
            fun getByKey(mKey: String): Type? {
                for (it in values()) {
                    if (it.key.equals(mKey, true)) {
                        return it
                    }
                }
                return null
            }
        }
    }

    fun getType(): Type

    fun getIntent(context: Context): Intent

    fun getTitle(): String

    fun getBody(): String

    fun getColor(): String

    fun getIcon(): String

    fun getParam(): String

    fun getId(): String
}