package com.unilever.julia.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.unilever.julia.R
import com.unilever.julia.firebase.parser.FirebaseNotification
import com.unilever.julia.ui.autenticacao.AutenticacaoActivity
import com.unilever.julia.utils.RedirectIntents
import javax.inject.Inject

class SplashActivity : AutenticacaoActivity(), SplashView {

    @Inject
    lateinit var mPresenter : SplashPresenter<SplashView, SplashInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        val notification = intent.getParcelableExtra<FirebaseNotification>(RedirectIntents.EXTRA_NOTIFICATION)
        val isLogout = intent.getBooleanExtra(RedirectIntents.EXTRA_LOGOUT, false)

        Handler().postDelayed({ mPresenter.init(isLogout, notification) }, 1000)
    }

    override fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.handleInteractiveRequestRedirect(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsReadPhoneState(permissionGranted: Boolean) {
        mPresenter.sendTokenFirebase(permissionGranted)
    }
}