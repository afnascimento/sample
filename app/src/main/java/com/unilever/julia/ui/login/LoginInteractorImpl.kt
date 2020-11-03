package com.unilever.julia.ui.login

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.autenticacao.AutenticacaoInteractorImpl

class LoginInteractorImpl
@Inject constructor(mDataManager: DataManager) : AutenticacaoInteractorImpl(mDataManager), LoginInteractor