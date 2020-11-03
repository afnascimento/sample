package com.unilever.julia.ui.autenticacao

import android.content.Intent
import com.unilever.julia.ui.base.BaseView

interface AutenticacaoView : BaseView {
    fun redirectLogin()
    fun redirectTutorial()
    fun getImeiDevice(): String
    fun onRequestPermissionsReadPhoneState(permissionGranted : Boolean)
    fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?)
}