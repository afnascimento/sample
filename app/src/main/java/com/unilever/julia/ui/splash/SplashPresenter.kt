package com.unilever.julia.ui.splash

import android.content.Intent
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.autenticacao.AutenticacaoPresenter

interface SplashPresenter<V : SplashView, I : SplashInteractor> : AutenticacaoPresenter<V, I> {

    fun init(isLogout: Boolean, notification: FirebaseNotification?)
    //fun errorTokenGraph()
    fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?)
}