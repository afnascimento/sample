package com.unilever.julia.logger

import org.apache.commons.lang3.StringUtils

class LoggerMock : AbstractTimberTree() {

    private fun writterLog(tag: String?, msg: String) {
        System.out.println("${StringUtils.trimToEmpty(tag)} - $msg")
    }

    override fun verbose(tag: String?, msg: String) {
        writterLog(tag, msg)
    }

    override fun debug(tag: String?, msg: String) {
        writterLog(tag, msg)
    }

    override fun info(tag: String?, msg: String) {
        writterLog(tag, msg)
    }

    override fun warn(tag: String?, msg: String) {
        writterLog(tag, msg)
    }

    override fun error(tag: String?, msg: String, t: Throwable?) {
        System.err.println("${StringUtils.trimToEmpty(tag)} - $msg")
    }
}