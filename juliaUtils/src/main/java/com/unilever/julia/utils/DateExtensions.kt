package com.unilever.julia.utils

import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

fun Date.year(): Int {
    return DateTime(this).year
}

fun Date.monthOfYear(): Int {
    return DateTime(this).monthOfYear
}

fun Date.monthOfYearAsText(): String {
    return DateTime(this).monthOfYear().getAsText(Locale.getDefault())
}

fun Date.parseString(parsePatterns : ParsePatternsEnums): String {
    val format = SimpleDateFormat(parsePatterns.parttern, Locale.getDefault())
    return format.format(this)
}