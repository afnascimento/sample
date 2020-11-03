package com.unilever.julia.ui.agenda

import javax.inject.Inject

import com.unilever.julia.data.DataManager
import com.unilever.julia.ui.base.BaseInteractorImpl

class AgendaInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), AgendaInteractor