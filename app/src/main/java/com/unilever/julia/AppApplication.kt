package com.unilever.julia

import com.unilever.julia.dagger.DaggerAppComponent
import com.unilever.julia.data.DataManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import com.unilever.julia.firebase.FirebaseCrashApp
import com.unilever.julia.glide.GlideImage
import com.unilever.julia.logger.Logger
import com.unilever.julia.video.ExoPlayerManagerImpl
import com.unilever.julia.video.IExoPlayerApplication
import com.unilever.julia.video.IExoPlayerManager
import javax.inject.Inject

/**
 * Created by Andre on 19/12/2018.
 */
class AppApplication : DaggerApplication(), IExoPlayerApplication {

    /*
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
     */

    @Inject
    lateinit var dataManager: DataManager

    private lateinit var mExoPlayerManager : IExoPlayerManager

    override fun onCreate() {
        super.onCreate()
        FirebaseCrashApp.init(this)
        Logger.init()
        GlideImage.init(this)
        mExoPlayerManager = ExoPlayerManagerImpl(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        return appComponent
    }

    override fun getExoPlayerManager(): IExoPlayerManager {
        return mExoPlayerManager
    }
}