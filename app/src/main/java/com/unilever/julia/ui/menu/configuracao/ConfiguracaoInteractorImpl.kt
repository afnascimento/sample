package com.unilever.julia.ui.menu.configuracao

import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.InitDataConfiguracao
import com.unilever.julia.ui.base.BaseInteractorImpl
import io.reactivex.Observable
import javax.inject.Inject

class ConfiguracaoInteractorImpl
@Inject constructor(dataManager: DataManager) : BaseInteractorImpl(dataManager), ConfiguracaoInteractor {
    override fun getInitData(): Observable<InitDataConfiguracao> {
        return Observable.just(
            InitDataConfiguracao(
                dataManager().preferences().isTextToSpeech(),
                dataManager().preferences().isOutlook(),
                dataManager().preferences().isIPV()
            )
        )
    }
}