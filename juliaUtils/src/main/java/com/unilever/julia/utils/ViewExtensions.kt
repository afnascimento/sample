package com.unilever.julia.utils

import android.app.Activity
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun <T : View> Activity.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy { findViewById<T>(res) }
}

fun <T : View> Fragment.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy { view!!.findViewById<T>(res) }
}

fun <T : View> View.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy { findViewById<T>(res) }
}

fun View.toast(text : String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun View.toast(@StringRes resId : Int) {
    Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show()
}

fun TextView.setAbreviateText(lines : Int) {
    //setSingleLine(true)
    maxLines = lines
    ellipsize = TextUtils.TruncateAt.END
    //setSingleLine(true)
}