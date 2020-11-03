package com.unilever.julia.ui.base

import android.content.Context
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.Disposable

/**
 * Created by Andre on 27/12/2018.
 */
abstract class BaseViewFragment : DaggerFragment(), BaseView {

    private val mBaseView = BaseViewImpl()

    override fun onDestroy() {
        mBaseView.onDestroy()
        super.onDestroy()
    }

    override fun addDisposable(disposable: Disposable) {
        mBaseView.addDisposable(disposable)
    }

    override fun getContext(): Context {
        return activity!!
    }

    override fun showProgressDialog() {
        mBaseView.showProgressDialog(activity!!)
    }

    override fun hideProgressDialog() {
        mBaseView.hideProgressDialog()
    }
}