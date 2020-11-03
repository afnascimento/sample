package com.unilever.julia.ui.base

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.MaterialDialog
import io.reactivex.disposables.Disposable

/**
 * Created by andre.nascimento on 27/12/2018.
 */
interface BaseView {
    fun getContext(): Context
    fun addDisposable(disposable: Disposable)
    //fun showContentLoading()
    //fun hideContentLoading()
    fun onErrorHandler(e: Throwable, listener: LoadView.OnClickListener)
    fun showToast(text: String)
    fun showToast(@StringRes resId: Int)
    fun redirectSplash()
    fun redirectChat()
    fun showProgressDialog()
    fun hideProgressDialog()
    fun showLoadViewEmpty(title: String, text: String)
    fun showLoadViewEmpty(title: String, text: String, textButton: String, listener: LoadView.OnClickListener)
    fun showLoadViewEmpty(contentViews : List<View>, loadView: LoadView, title: String, text: String)
    fun showLoadView()
    fun hideLoadView()
    fun onErrorHandlerDialog(e: Throwable, listener: MaterialDialog.OnClickListener)
    fun getTextString(@StringRes resId: Int): String
    fun getTextString(@StringRes resId: Int, vararg formatArgs: Any): String
    fun redirectChat(notification: FirebaseNotification)
    fun redirectNotificationDetail(notification: FirebaseNotification)
    fun showLoadView(contentView: View, loadView: LoadView)
    fun hideLoadView(contentView: View, loadView: LoadView)
    fun onErrorHandler(
        contentView: View,
        loadView: LoadView,
        e: Throwable,
        listener: LoadView.OnClickListener
    )
    fun showLoadView(contentViews: List<View>, loadView: LoadView)
    fun hideLoadView(contentViews: List<View>, loadView: LoadView)
    fun onErrorHandler(
        contentViews: List<View>,
        loadView: LoadView,
        e: Throwable,
        listener: LoadView.OnClickListener
    )
}