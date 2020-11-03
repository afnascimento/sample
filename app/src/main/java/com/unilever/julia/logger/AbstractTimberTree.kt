package com.unilever.julia.logger

import android.util.Log
import com.unilever.julia.BuildConfig
import timber.log.Timber

abstract class AbstractTimberTree : Timber.DebugTree() {

    abstract fun verbose(tag : String?, msg : String)
    abstract fun debug(tag : String?, msg : String)
    abstract fun info(tag : String?, msg : String)
    abstract fun warn(tag : String?, msg : String)
    abstract fun error(tag : String?, msg : String, t: Throwable?)

    @Suppress("ConstantConditionIf")
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (BuildConfig.IS_PRINT_LOG) {
            when (priority) {
                Log.VERBOSE -> verbose(tag, message)
                Log.DEBUG -> debug(tag, message)
                Log.INFO -> info(tag, message)
                Log.WARN -> warn(tag, message)
                Log.ERROR -> error(tag, message, t)
            }
        }
    }
}