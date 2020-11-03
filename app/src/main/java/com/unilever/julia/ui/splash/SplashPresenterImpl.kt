package com.unilever.julia.ui.splash

import android.content.Intent
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalException
import com.unilever.julia.data.model.User
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.autenticacao.AutenticacaoPresenterImpl
import com.unilever.julia.msal.IMsalClientHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SplashPresenterImpl<V : SplashView, I : SplashInteractor>
@Inject constructor(mView: V, mInteractor: I, mMsalClient : IMsalClientHandler) : AutenticacaoPresenterImpl<V, I>(mView, mInteractor, mMsalClient), SplashPresenter<V, I> {

    override fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            if (data == null) {
                onHandlerError()
                return
            }
            mClientApplication.handleInteractiveRequestRedirect(requestCode, resultCode, data)
        } catch (e : Throwable) {
            onHandlerError()
        }
    }

    override fun init(isLogout: Boolean, notification: FirebaseNotification?) {
        if (isLogout) {
            logout()
            return
        }
        mClientApplication.getAccounts { accounts ->
            if (accounts.isNullOrEmpty() || accounts.size > 1) {
                onHandlerError()
            } else {
                acquireTokenSilentAsync(accounts[0], notification)
            }
        }
    }

    private fun acquireTokenSilentAsync(account : IAccount, notification: FirebaseNotification?) {
        mClientApplication.acquireTokenSilentAsync(mScopes, account, object : AuthenticationCallback {
            override fun onSuccess(authenticationResult: IAuthenticationResult?) {
                onHandlerSuccess(authenticationResult, notification)
            }

            override fun onCancel() {
                onHandlerError()
            }

            override fun onError(exception: MsalException?) {
                onHandlerError()
            }
        })
    }

    private fun onHandlerSuccess(authenticationResult: IAuthenticationResult?, notification: FirebaseNotification?) {
        if (authenticationResult == null) {
            onHandlerError()
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
                        onHandlerSuccess(notification)
                    }

                    override fun onError(e: Throwable) {
                        onHandlerError()
                    }
                })
        )
    }

    private fun onHandlerError() {
        getView().redirectLogin()
    }

    private fun onHandlerSuccess(notification: FirebaseNotification?) {
        sendTokenFirebase(notification)
    }
}