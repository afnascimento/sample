package com.unilever.julia.ui.base

import android.app.Activity
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.components.DialogProgress
import com.unilever.julia.components.enums.IconEnums
import com.unilever.julia.exception.EmptyDataException
import com.unilever.julia.exception.ExternalServerOutException
import com.unilever.julia.exception.InternetConnectionException
import com.unilever.julia.exception.TimeoutConnectionException
import com.unilever.julia.components.LoadView
import com.unilever.julia.utils.MaterialDialog
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.apache.commons.lang3.StringUtils
import java.net.UnknownHostException

/**
 * Created by Andre on 27/12/2018.
 */
class BaseViewImpl {

    val compositeDisposable = CompositeDisposable()

    fun onDestroy() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun showJuliaLoading(activity: Activity, contentViews : List<View?>?, contentLoad: LoadView?) {
        activity.runOnUiThread {
            hideViews(contentViews)
            contentLoad?.showLoading()
        }
    }

    fun hideJuliaLoading(activity: Activity, contentViews: List<View?>?, contentLoad: LoadView?) {
        activity.runOnUiThread {
            showViews(contentViews)
            contentLoad?.hide()
        }
    }

    private fun showViews(views: List<View?>?) {
        if (!views.isNullOrEmpty()) {
            for (view in views) {
                view?.visibility = View.VISIBLE
            }
        }
    }

    private fun hideViews(contentViews: List<View?>?) {
        if (!contentViews.isNullOrEmpty()) {
            for (view in contentViews) {
                view?.visibility = View.GONE
            }
        }
    }

    private fun showErrorOffline(activity: Activity,
                                 contentViews : List<View?>?,
                                 contentLoadView: LoadView?,
                                 title: String, text: String, listener : LoadView.OnClickListener) {
        activity.runOnUiThread {
            hideViews(contentViews)
            contentLoadView?.showOffline(title, text, listener)
        }
    }

    private fun showError(activity: Activity,
                          contentViews : List<View?>?,
                          contentLoadView: LoadView?,
                          iconError : String,
                          title: String,
                          text: String,
                          textButton: String,
                          listener : LoadView.OnClickListener) {
        activity.runOnUiThread {
            hideViews(contentViews)
            contentLoadView?.showCustom(0, iconError, title, text, textButton, listener)
        }
    }

    fun showEmpty(activity: Activity,
                  contentViews : List<View?>?,
                  contentLoadView: LoadView?,
                  title: String, text: String,
                  textButton: String,
                  listener : LoadView.OnClickListener) {
        activity.runOnUiThread {
            hideViews(contentViews)
            contentLoadView?.showEmpty(title, text, textButton, listener)
        }
    }

    fun showEmpty(activity: Activity, contentViews : List<View?>?, contentLoadView: LoadView?, title: String, text: String) {
        activity.runOnUiThread {
            hideViews(contentViews)
            contentLoadView?.showEmpty(title, text)
        }
    }

    fun onErrorHandler(
        activity: BaseViewActivity,
        contentViews : List<View?>?,
        contentLoadView: LoadView?,
        e: Throwable,
        listener: LoadView.OnClickListener) {

        when (e) {
            is UnknownHostException -> showErrorOffline(
                activity, contentViews, contentLoadView,
                activity.getString(R.string.julia_load_offline_title),
                activity.getString(R.string.julia_load_offline_text),
                listener
            )
            is InternetConnectionException -> showErrorOffline(
                activity, contentViews, contentLoadView,
                activity.getString(R.string.julia_load_offline_title),
                activity.getString(R.string.julia_load_offline_text),
                listener
            )
            is TimeoutConnectionException -> showError(
                activity, contentViews, contentLoadView,
                IconEnums.ICON_ERROR_FACE.iconHexa,
                activity.getString(R.string.julia_load_timeout_title),
                activity.getString(R.string.julia_load_timeout_text),
                activity.getString(R.string.julia_load_tentar_nov),
                listener)
            is ExternalServerOutException -> {
                val title = StringUtils.defaultIfEmpty(e.textTitle, activity.getString(R.string.julia_load_timeout_title))
                val text = StringUtils.defaultIfEmpty(e.textMessage, activity.getString(R.string.julia_load_timeout_text))
                val icon = StringUtils.defaultIfEmpty(e.icon, IconEnums.ICON_ERROR_FACE.iconHexa)
                val textButton = activity.getString(R.string.julia_load_tentar_nov)
                showError(activity, contentViews, contentLoadView, icon, title, text, textButton, listener)
            }
            is EmptyDataException -> {
                val title = StringUtils.defaultIfEmpty(e.title, activity.getString(R.string.julia_load_empty_title))
                val text = StringUtils.defaultIfEmpty(e.description, activity.getString(R.string.julia_load_empty_text))
                val textButton = activity.getString(R.string.julia_load_tentar_nov)
                showError(
                    activity, contentViews, contentLoadView,
                    IconEnums.ICON_ERROR_FACE.iconHexa,
                    title,
                    text,
                    textButton,
                    listener)
            }
            else -> {
                showError(
                    activity, contentViews, contentLoadView,
                    IconEnums.ICON_ERROR_FACE.iconHexa,
                    activity.getString(R.string.julia_load_generic_title),
                    activity.getString(R.string.julia_load_generic_text),
                    activity.getString(R.string.julia_load_tentar_nov),
                    listener)
            }
        }
    }

    fun onErrorHandlerDialog(
        activity: BaseViewActivity,
        e: Throwable,
        listener: MaterialDialog.OnClickListener) {

        val titleDialog: String
        val messageDialog: String

        when (e) {
            is UnknownHostException -> {
                titleDialog = activity.getString(R.string.julia_load_offline_title)
                messageDialog = activity.getString(R.string.julia_load_offline_text)
            }
            is TimeoutConnectionException -> {
                titleDialog = activity.getString(R.string.julia_load_timeout_title)
                messageDialog = activity.getString(R.string.julia_load_timeout_text)
            }
            is EmptyDataException -> {
                titleDialog = activity.getString(R.string.julia_load_empty_title)
                if (StringUtils.isBlank(e.message)) {
                    messageDialog = activity.getString(R.string.julia_load_empty_text)
                } else {
                    messageDialog = StringUtils.trimToEmpty(e.message)
                }
            }
            else -> {
                titleDialog = activity.getString(R.string.julia_load_generic_title)
                messageDialog = activity.getString(R.string.julia_load_generic_text)
            }
        }

        MaterialDialog(activity)
            .setTitle(titleDialog)
            .setMessage(messageDialog)
            .setPositiveButton(activity.getString(R.string.entendi), listener)
            .show()
    }

    private var progress: DialogProgress? = null

    fun showProgressDialog(activity: Activity) {
        progress = DialogProgress(activity)
        progress?.show()
    }

    fun hideProgressDialog() {
        progress?.dismiss()
        progress = null
    }
}