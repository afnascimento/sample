package com.unilever.julia.ui.menu.configuracao

import com.unilever.julia.data.model.InitDataConfiguracao
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface ConfiguracaoInteractor: BaseInteractor {
    fun getInitData(): Observable<InitDataConfiguracao>
}