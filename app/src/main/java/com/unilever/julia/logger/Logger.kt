package com.unilever.julia.logger

import timber.log.Timber

object Logger {

    fun init() {
        Timber.plant(LoggerTimber())
    }

    fun debug(tag: String?, message: String) {
        Timber.tag(tag).d(message)
    }

    fun error(tag: String?, message: String, t: Throwable?) {
        Timber.tag(tag).e(t, message)
    }
}