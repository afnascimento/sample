package com.unilever.julia.ui.autenticacao

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.InstanceIdResult
import com.microsoft.identity.client.IAuthenticationResult
import com.unilever.julia.data.model.User
import com.unilever.julia.ui.base.BaseInteractor
import io.reactivex.Observable

interface AutenticacaoInteractor : BaseInteractor {
    fun callGraphAPI(authenticationResult: IAuthenticationResult): Observable<User>
    //fun isTutorialFinish(): Observable<Boolean>
    fun isTutorialFinish(): Boolean
    fun getTokenFirebase(): Observable<Task<InstanceIdResult>>
    fun sendTokenFirebase(imei: String): Observable<Boolean>
    fun saveBanner(context: Context): Observable<Boolean>
    fun sendTokenFirebaseWithIdFirebaseToken(): Observable<Boolean>
}