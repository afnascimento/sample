package com.unilever.julia.ui.status.detalhe

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.base.BaseInteractorImpl

class StatusDetalheInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), StatusDetalheInteractor {

}