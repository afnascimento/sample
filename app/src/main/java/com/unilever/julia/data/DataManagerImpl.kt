package com.unilever.julia.data

import com.unilever.julia.data.api.JuliaUnileverApi
import com.unilever.julia.data.api.MicrosoftApi
import com.unilever.julia.data.database.DatabaseHelper
import com.unilever.julia.data.preferences.PreferencesHelper
import com.unilever.julia.firebase.FirebaseAnalyticsApp

class DataManagerImpl(private var mJuliaUnileverApi: JuliaUnileverApi,
                      private var mMicrosoftApi: MicrosoftApi,
                      private var mPreferences: PreferencesHelper,
                      private var mFirebaseAnalytics: FirebaseAnalyticsApp,
                      private var mDatabase: DatabaseHelper) : DataManager {

    override fun database(): DatabaseHelper {
        return mDatabase
    }

    override fun preferences(): PreferencesHelper {
        return mPreferences
    }

    override fun juliaApi(): JuliaUnileverApi {
        return mJuliaUnileverApi
    }

    override fun microsoftApi(): MicrosoftApi {
        return mMicrosoftApi
    }

    override fun firebaseAnalytics(): FirebaseAnalyticsApp {
        return mFirebaseAnalytics
    }
}