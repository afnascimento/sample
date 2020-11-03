package com.unilever.julia.firebase

import android.content.Context
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.unilever.julia.BuildConfig
import com.unilever.julia.data.model.User
import io.fabric.sdk.android.Fabric

object FirebaseCrashApp {

    fun init(context: Context) {
        val disabled = BuildConfig.DEBUG

        // Set up Crashlytics, disabled for debug builds
        val crashlyticsKit = Crashlytics.Builder()
            .core(CrashlyticsCore.Builder().disabled(disabled).build())
            .build()

        // Initialize Fabric with the debug-disabled crashlytics.
        Fabric.with(context, crashlyticsKit)
    }

    fun setLoggerUser(user: User) {
        Crashlytics.setUserIdentifier(user.mail)
        Crashlytics.setUserName(user.getUsername())
        //Crashlytics.setUserEmail("")
    }

    fun forceCrash() {
        Crashlytics.getInstance().crash()
    }
}