package com.unilever.julia.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.unilever.julia.R
import com.unilever.julia.ui.autenticacao.AutenticacaoActivity
import kotlinx.android.synthetic.main.login_center.*
import javax.inject.Inject

class LoginActivity : AutenticacaoActivity(), LoginView {

    @Inject lateinit var mPresenter : LoginPresenter<LoginView, LoginInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        btnEntrar.setOnClickListener {
            mPresenter.acquireToken(this@LoginActivity)
        }

        mPresenter.acquireToken(this@LoginActivity)
    }

    override fun showLoading() {
        progress.visibility = View.VISIBLE
        btnEntrar.visibility = View.GONE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        btnEntrar.visibility = View.VISIBLE
    }

    override fun handleInteractiveRequestRedirect(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.handleInteractiveRequestRedirect(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsReadPhoneState(permissionGranted: Boolean) {
        mPresenter.sendTokenFirebase(permissionGranted)
    }
}