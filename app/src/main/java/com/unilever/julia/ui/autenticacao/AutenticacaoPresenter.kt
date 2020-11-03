package com.unilever.julia.ui.autenticacao

import android.app.Activity
import android.content.Intent
import com.unilever.julia.ui.base.BasePresenter

interface AutenticacaoPresenter<V : AutenticacaoView, I : AutenticacaoInteractor> : BasePresenter<V, I> {

    //fun acquireToken(activity: Activity)
    //fun isLogged(activity: Activity)
    //fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun logout()

    fun sendTokenFirebase(permissionGranted: Boolean)
}