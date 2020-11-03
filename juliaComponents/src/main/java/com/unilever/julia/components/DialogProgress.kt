package com.unilever.julia.components

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.core.content.ContextCompat

class DialogProgress(private val context: Context) {

    private var dialog: Dialog? = null

    fun show() {
        if (!isShowing()) {
            dialog = Dialog(context, R.style.DialogProgress)
            dialog?.setCancelable(false)
            dialog?.setCanceledOnTouchOutside(false)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.setContentView(R.layout.dialog_progress)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)))
            dialog?.show()
        }
    }

    fun dismiss() {
        if (isShowing()) {
            dialog?.dismiss()
            dialog = null
        }
    }

    fun isShowing(): Boolean {
        return dialog?.isShowing ?: false
    }
}