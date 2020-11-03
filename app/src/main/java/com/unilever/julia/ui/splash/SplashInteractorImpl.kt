package com.unilever.julia.ui.splash

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.autenticacao.AutenticacaoInteractorImpl

class SplashInteractorImpl
@Inject constructor(mDataManager: DataManager) : AutenticacaoInteractorImpl(mDataManager), SplashInteractor