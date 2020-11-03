package com.unilever.julia.ui.login

import com.unilever.julia.ui.autenticacao.AutenticacaoView

interface LoginView : AutenticacaoView {
    fun showLoading()
    fun hideLoading()
}