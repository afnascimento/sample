package com.unilever.julia.ui.inovacao.detalhe

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.base.BaseInteractorImpl

class InovacaoDetalheInteractorImpl
@Inject
constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), InovacaoDetalheInteractor