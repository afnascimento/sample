package com.unilever.julia.ui.status.pendentes

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.base.BaseInteractorImpl

class StatusPendentesInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), StatusPendentesInteractor