package com.unilever.julia.utils

import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.time.DateUtils
import java.lang.StringBuilder
import java.net.URL
import java.util.*

fun String.isValidURL(): Boolean {
    try {
        val url = URL(this)
        return true
    } catch (e : Throwable) {
        return false
    }
}

fun String.capitalizeAllText(): String {
    val lowerCase = StringUtils.lowerCase(StringUtils.normalizeSpace(this))
    val split = StringUtils.split(lowerCase, " ")
    if (split.isNotEmpty()) {
        val builder = StringBuilder()
        for (text in split) {
            builder.append(StringUtils.capitalize(text)).append(" ")
        }
        return StringUtils.removeEnd(builder.toString(), " ")
    }
    return StringUtils.capitalize(lowerCase)
}

fun String.parseDate(vararg parsePatterns : ParsePatternsEnums): Date? {
    if (this.isNotEmpty()) {

        val arrayListOf = arrayListOf<String>()

        for (it in parsePatterns) {
            arrayListOf.add(it.parttern)
        }

        return DateUtils.parseDate(this, *arrayListOf.toTypedArray())
    }
    return null
}