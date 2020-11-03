package com.unilever.julia.ui.login

import android.app.Activity
import android.content.Intent
import com.unilever.julia.ui.autenticacao.AutenticacaoPresenter

interface LoginPresenter<V : LoginView, I : LoginInteractor> : AutenticacaoPresenter<V, I> {
    fun acquireToken(activity: Activity)
    fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?)
}