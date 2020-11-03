package com.unilever.julia.dagger

import android.app.Application
import android.content.Context
import com.google.crypto.tink.*
import com.google.crypto.tink.config.TinkConfig
import com.unilever.julia.FlavorConfig
import com.unilever.julia.cripto.EnviromentDecrypt
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.DataManagerImpl
import com.unilever.julia.data.api.*
import com.unilever.julia.googleTink.PreferencesTink
import com.unilever.julia.googleTink.PreferencesTinkImpl
import com.unilever.julia.data.database.AppRoomDatabase
import com.unilever.julia.data.database.DatabaseHelper
import com.unilever.julia.data.database.DatabaseHelperImpl
import com.unilever.julia.data.model.Enviroment
import com.unilever.julia.data.preferences.PreferencesHelper
import com.unilever.julia.data.preferences.PreferencesHelperImpl
import com.unilever.julia.firebase.FirebaseAnalyticsApp
import com.unilever.julia.msal.IMsalClientHandler
import com.unilever.julia.msal.MsalClientHandler
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by andre.nascimento on 19/12/2018.
 */
@Module
class AppModule {

    /*
    @Provides
    @Singleton
    internal fun provideIJuliaUnileverApiConfig(context: Context, preferences: PreferencesHelper): IJuliaUnileverApiConfig {
        return IJuliaUnileverApiConfigImpl(context, preferences)
    }

    @Provides
    @Singleton
    internal fun provideISSLContextApi(context: Context): ICertificateApi? {
        return ICertificateApiImpl(context)
    }
    */

    @Provides
    @Singleton
    internal fun provideApplication(application: Application): Context {
        Config.register(TinkConfig.LATEST)
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideEnviroment(context: Context): Enviroment {
        return EnviromentDecrypt().getEnviroment(context)
    }

    @Provides
    @Singleton
    internal fun providePreferencesTink(context: Context): PreferencesTink {
        return PreferencesTinkImpl(context)
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(context: Context, preferencesTink: PreferencesTink): PreferencesHelper {
        return PreferencesHelperImpl(context, preferencesTink)
    }

    @Provides
    @Singleton
    internal fun provideJuliaUnileverApi(context: Context,
                                         enviroment: Enviroment,
                                         preferences: PreferencesHelper): JuliaUnileverApi {
        val apiConfig = IJuliaUnileverApiConfigImpl(enviroment.broker, preferences)
        val certificateApi = FlavorConfig.getCertificateApi(context, enviroment.certificate)
        return JuliaUnileverApiImpl(apiConfig, certificateApi)
    }

    @Provides
    @Singleton
    internal fun provideMicrosoftApi(): MicrosoftApi {
        return MicrosoftApiImpl()
    }

    @Provides
    @Singleton
    internal fun provideFirebaseAnalyticsApp(context: Context, preferences: PreferencesHelper): FirebaseAnalyticsApp {
        return FirebaseAnalyticsApp(context, preferences)
    }

    @Provides
    @Singleton
    internal fun provideDatabase(context: Context): DatabaseHelper {
        return DatabaseHelperImpl(AppRoomDatabase.getInstance(context))
    }

    @Provides
    @Singleton
    internal fun provideDataManager(juliaUnileverApi: JuliaUnileverApi,
                                    microsoftApi: MicrosoftApi,
                                    preferences: PreferencesHelper,
                                    firebaseAnalytics: FirebaseAnalyticsApp,
                                    database: DatabaseHelper): DataManager {
        return DataManagerImpl(juliaUnileverApi, microsoftApi, preferences, firebaseAnalytics, database)
    }

    @Provides
    @Singleton
    internal fun getMsalConfigFile(context: Context): IMsalClientHandler {
        return MsalClientHandler(context)
    }
}