package com.unilever.julia.ui.login

import android.app.Activity
import android.content.Intent
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.exception.MsalException
import com.unilever.julia.R
import com.unilever.julia.data.model.User
import com.unilever.julia.ui.autenticacao.AutenticacaoPresenterImpl
import com.unilever.julia.msal.IMsalClientHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenterImpl<V : LoginView, I : LoginInteractor>
@Inject constructor(mView: V, mInteractor: I, mMsalClient : IMsalClientHandler) : AutenticacaoPresenterImpl<V, I>(mView, mInteractor, mMsalClient), LoginPresenter<V, I>, AuthenticationCallback {

    override fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (data != null) {
                mClientApplication.handleInteractiveRequestRedirect(requestCode, resultCode, data)
            } else {
                onError()
            }
        } catch (e : Throwable) {
            onError()
        }
    }

    /* Use MSAL to acquireToken for the end-user
     * Callback will call Graph juliaApi w/ access token & update UI
     */
    override fun acquireToken(activity: Activity) {
        getView().showLoading()
        mClientApplication.acquireToken(activity, mScopes, this@LoginPresenterImpl)
    }

    override fun onSuccess(authenticationResult: IAuthenticationResult?) {
        callGraphAPI(authenticationResult)
    }

    override fun onCancel() {
        onError()
    }

    override fun onError(exception: MsalException?) {
        onError()
    }

    private fun callGraphAPI(authenticationResult: IAuthenticationResult?) {
        if (authenticationResult == null) {
            onError()
            return
        }

        getView().addDisposable(
            getInteractor().callGraphAPI(authenticationResult)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<User>() {
                    override fun onNext(user: User) {
                    }

                    override fun onComplete() {
                        onSuccess()
                    }

                    override fun onError(e: Throwable) {
                        onError()
                    }
                })
        )
    }

    private fun onError() {
        getView().hideLoading()
        getView().showToast(R.string.login_error)
    }

    private fun onSuccess() {
        sendTokenFirebase(null)
    }
}