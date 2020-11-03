package com.unilever.julia.data

import com.unilever.julia.data.api.JuliaUnileverApi
import com.unilever.julia.data.api.MicrosoftApi
import com.unilever.julia.data.database.DatabaseHelper
import com.unilever.julia.data.preferences.PreferencesHelper
import com.unilever.julia.firebase.FirebaseAnalyticsApp

interface DataManager {

    fun juliaApi(): JuliaUnileverApi

    fun microsoftApi(): MicrosoftApi

    fun preferences(): PreferencesHelper

    fun firebaseAnalytics(): FirebaseAnalyticsApp

    fun database(): DatabaseHelper
}