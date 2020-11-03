package com.unilever.julia.ui.autenticacao

import android.content.Context
import android.os.Build
import com.microsoft.identity.client.PublicClientApplication
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.logger.Logger
import com.unilever.julia.msal.IMsalClientHandler
import com.unilever.julia.ui.base.BasePresenterImpl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils

open class AutenticacaoPresenterImpl<V : AutenticacaoView, I : AutenticacaoInteractor>
constructor(mView: V, mInteractor: I, mMsalClient : IMsalClientHandler) : BasePresenterImpl<V, I>(mView, mInteractor), AutenticacaoPresenter<V, I> {

    internal val mScopes = mMsalClient.msalScopes()

    internal val mClientApplication = PublicClientApplication(mView.getContext(), mMsalClient.msalFileConfig())

    override fun logout() {
        mClientApplication.getAccounts { accounts ->
            val user = getInteractor().getUserLogged()

            for (account in accounts) {
                if (StringUtils.equalsIgnoreCase(account.username, user.accountId)) {
                    mClientApplication.removeAccount(account) { isSuccess ->
                        val removeAccountSuccess = (isSuccess != null && isSuccess)
                        if (removeAccountSuccess) {
                            Logger.debug("LOGOUT", "account ${account.username} removed")
                        }
                    }
                }
            }
            getView().redirectLogin()
        }
    }

    private var mNotification: FirebaseNotification? = null

    private fun getImeiWithVerificationAndroid10(): String {
        // android <= android 9 Pie
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return getView().getImeiDevice()
        } else {
            return ""
        }
    }

    fun sendTokenFirebase(notification: FirebaseNotification?) {
        mNotification = notification
        val sdk = Build.VERSION.SDK_INT
        // android >= android 10
        if (sdk >= 29) {
            sendTokenFirebase(getView().getContext(), mNotification, getInteractor().sendTokenFirebaseWithIdFirebaseToken())
        } else {
            val imeiDevice = getView().getImeiDevice()
            if (imeiDevice.isNotEmpty()) {
                sendTokenFirebase(getView().getContext(), mNotification, getInteractor().sendTokenFirebase(imeiDevice))
            }
        }
    }

    override fun sendTokenFirebase(permissionGranted: Boolean) {
        if (permissionGranted) {
            val imeiDevice = getView().getImeiDevice()
            sendTokenFirebase(getView().getContext(), mNotification, getInteractor().sendTokenFirebase(imeiDevice))
        } else {
            sendTokenFirebase(getView().getContext(), mNotification, getInteractor().sendTokenFirebaseWithIdFirebaseToken())
        }
    }

    //private fun sendTokenFirebase(context: Context, notification: FirebaseNotification?, imeiDevice : String) {
    private fun sendTokenFirebase(context: Context, notification: FirebaseNotification?, observableSendToken : Observable<Boolean>) {
        getView().addDisposable(
            observableSendToken
                .flatMap {
                    return@flatMap getInteractor().saveBanner(context)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Boolean>() {
                    override fun onNext(value: Boolean) {
                    }

                    override fun onComplete() {
                        checkActionRedirect(notification)
                    }

                    override fun onError(e: Throwable) {
                        checkActionRedirect(notification)
                    }
                })
        )
    }

    private fun checkActionRedirect(notification: FirebaseNotification?) {
        when {
            notification == null -> checkTutorial()
            notification.getType() == FirebaseNotification.Type.NONE -> checkTutorial()
            notification.getType() == FirebaseNotification.Type.TEXT -> {
                getView().redirectChat(notification)
            }
            notification.getType() == FirebaseNotification.Type.CODE -> {
                getView().redirectNotificationDetail(notification)
            }
        }
    }

    private fun checkTutorial() {
        if (getInteractor().isTutorialFinish()) {
            getView().redirectChat()
        } else {
            getView().redirectTutorial()
        }
    }

    /*
    private fun sendErrorTokenFirebase(message : String) {
        try {
            throw TokenSendException(message)
        } catch (e : Throwable) {
            Crashlytics.logException(e)
            //Crashlytics.log("Teste Erro")
            Crashlytics.setUserName(mUser?.getUsername())
            Crashlytics.setUserEmail(mUser?.mail)
        }
    }
    */
}