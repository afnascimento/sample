package com.unilever.julia.ui.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import android.view.View
import com.unilever.julia.BuildConfig
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.MaterialDialog
import com.unilever.julia.utils.RedirectIntents
import com.unilever.julia.utils.Utils
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.Disposable

/**
 * Created by andre.nascimento on 27/12/2018.
 */
abstract class BaseViewActivity : DaggerAppCompatActivity(), BaseView {

    private val mBaseView = BaseViewImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("ConstantConditionIf")
        if (!BuildConfig.IS_PERMISSION_PRINT_SCREEN) {
            Utils.setFlagSecure(window)
        }
    }

    override fun onDestroy() {
        mBaseView.onDestroy()
        super.onDestroy()
    }

    override fun addDisposable(disposable: Disposable) {
        mBaseView.addDisposable(disposable)
    }

    override fun showLoadViewEmpty(title: String, text: String) {
        mBaseView.showEmpty(this, listOf(getLoadContent()), getLoadView(), title, text)
    }

    override fun showLoadViewEmpty(title: String, text: String, textButton: String, listener : LoadView.OnClickListener) {
        mBaseView.showEmpty(this, listOf(getLoadContent()), getLoadView(), title, text, textButton, listener)
    }

    override fun showLoadViewEmpty(contentViews : List<View>, loadView: LoadView, title: String, text: String) {
        mBaseView.showEmpty(this, contentViews, loadView, title, text)
    }

    override fun showLoadView() {
        val contentViews = getLoadContentViews()
        if (contentViews.isNullOrEmpty()) {
            mBaseView.showJuliaLoading(this, listOf(getLoadContent()), getLoadView())
        } else {
            mBaseView.showJuliaLoading(this, contentViews, getLoadView())
        }
    }

    override fun showLoadView(contentView : View, loadView: LoadView) {
        mBaseView.showJuliaLoading(this, listOf(contentView), loadView)
    }

    override fun showLoadView(contentViews : List<View>, loadView: LoadView) {
        mBaseView.showJuliaLoading(this, contentViews, loadView)
    }

    override fun hideLoadView() {
        val contentViews = getLoadContentViews()
        if (contentViews.isNullOrEmpty()) {
            mBaseView.hideJuliaLoading(this, listOf(getLoadContent()), getLoadView())
        } else {
            mBaseView.hideJuliaLoading(this, contentViews, getLoadView())
        }
    }

    override fun hideLoadView(contentView : View, loadView: LoadView) {
        mBaseView.hideJuliaLoading(this, listOf(contentView), loadView)
    }

    override fun hideLoadView(contentViews : List<View>, loadView: LoadView) {
        mBaseView.hideJuliaLoading(this, contentViews, loadView)
    }

    open fun getLoadView(): LoadView? {
        return null
    }

    open fun getLoadContent(): View? {
        return null
    }

    open fun getLoadContentViews(): List<View?>? {
        return null
    }

    override fun onErrorHandler(e: Throwable, listener : LoadView.OnClickListener) {
        mBaseView.onErrorHandler(this, listOf(getLoadContent()), getLoadView(), e, listener)
    }

    override fun onErrorHandler(contentView : View, loadView: LoadView, e: Throwable, listener : LoadView.OnClickListener) {
        mBaseView.onErrorHandler(this, listOf(contentView), loadView, e, listener)
    }

    override fun onErrorHandler(contentViews : List<View>, loadView: LoadView, e: Throwable, listener : LoadView.OnClickListener) {
        mBaseView.onErrorHandler(this, contentViews, loadView, e, listener)
    }

    override fun onErrorHandlerDialog(e: Throwable, listener : MaterialDialog.OnClickListener) {
        mBaseView.onErrorHandlerDialog(this, e, listener)
    }

    override fun getContext(): Context {
        return this
    }

    override fun showToast(text: String) {
        Utils.showToast(this, text)
    }

    override fun showToast(@StringRes resId: Int) {
        Utils.showToast(this, getString(resId))
    }

    override fun redirectSplash() {
        ActivityCompat.finishAffinity(this)
        //RedirectIntents.redirectSplashActivity(this)
    }

    override fun redirectChat() {
        RedirectIntents.redirectChatMainActivity(this)
    }

    override fun redirectChat(notification: FirebaseNotification) {
        RedirectIntents.redirectChatMainActivity(this, notification)
    }

    override fun redirectNotificationDetail(notification: FirebaseNotification) {
        RedirectIntents.redirectNotificationDetailActivity(this, notification)
    }

    override fun showProgressDialog() {
        mBaseView.showProgressDialog(this)
    }

    override fun hideProgressDialog() {
        mBaseView.hideProgressDialog()
    }

    override fun getTextString(@StringRes resId : Int): String {
        return getString(resId)
    }

    override fun getTextString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return getString(resId, *formatArgs)
    }
}