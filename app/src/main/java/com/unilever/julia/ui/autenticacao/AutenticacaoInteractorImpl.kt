package com.unilever.julia.ui.autenticacao

import android.content.Context
import androidx.core.net.toUri
import com.google.android.gms.tasks.Task
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.google.gson.GsonBuilder
import com.unilever.julia.data.DataManager
import com.unilever.julia.data.model.User
import com.unilever.julia.ui.base.BaseInteractorImpl
import com.microsoft.identity.client.IAuthenticationResult
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.model.Banner
import com.unilever.julia.data.model.Token
import com.unilever.julia.glide.GlideApp
import com.unilever.julia.logger.Logger
import com.unilever.julia.utils.isValidURL
import com.unilever.julia.worker.WorkerApp
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.apache.commons.lang3.StringUtils

open class AutenticacaoInteractorImpl
constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), AutenticacaoInteractor {

    override fun callGraphAPI(authenticationResult: IAuthenticationResult): Observable<User> {
        return dataManager().microsoftApi().callGraphAPI(authenticationResult.accessToken)
            .flatMap { profile ->
                val token = Token()
                token.mAccessToken = authenticationResult.accessToken
                token.mExpiresOn = authenticationResult.expiresOn
                token.mScopes = authenticationResult.scope
                dataManager().preferences().saveToken(token)

                Logger.debug("Graph", token.toString())

                val user = User()
                user.accountId = StringUtils.trimToEmpty(authenticationResult.account.username)
                user.mail = StringUtils.trimToEmpty(profile.mail)
                user.givenName = StringUtils.trimToEmpty(profile.givenName)
                user.surname = StringUtils.trimToEmpty(profile.surname)

                dataManager().preferences().saveUser(user)
                return@flatMap Observable.just(user)
            }
    }

    private val mGson = GsonBuilder()
        .registerTypeAdapter(Banner::class.java, Banner.Deserializer())
        .create()

    override fun saveBanner(context: Context): Observable<Boolean> {
        return sendIntent("", JuliaIntent.Intent.BANNER_00)
            .subscribeOn(Schedulers.io())
            .flatMap { conversations ->

                val json = StringUtils.trimToEmpty(conversations.getAnswer().technicalText)

                val banner = mGson.fromJson(json, Banner::class.java)

                return@flatMap Observable.just(banner)
            }
            .flatMap { banner ->

                if (banner.url.isValidURL()) {
                    return@flatMap getDownloadFile(context, banner)
                } else {
                    dataManager().preferences().saveBanner(banner)
                    return@flatMap Observable.just(true)
                }
            }
    }

    private fun getDownloadFile(context: Context, banner: Banner): Observable<Boolean> {
        return Observable.fromFuture(
            GlideApp
            .with(context)
            .asFile()
            .load(banner.url)
            .submit()
        ).flatMap { file ->
            banner.url = file.toUri().toString()
            dataManager().preferences().saveBanner(banner)

            return@flatMap Observable.just(true)
        }
    }

    override fun sendTokenFirebaseWithIdFirebaseToken(): Observable<Boolean> {
        return sendTokenFirebase("")
    }

    override fun sendTokenFirebase(imei: String): Observable<Boolean> {
        return getTokenFirebase().flatMap { task ->
            //if (!task.isSuccessful) {
            //    throw TokenSendException()
            //}

            val token : String = task.result?.token ?: ""

            var uniqueId = imei
            if (uniqueId.isEmpty()) {
                uniqueId =  task.result?.id ?: ""
            }
            Logger.debug("31_PREINICIO", token)
            Logger.debug("31_PREINICIO", uniqueId)

            val userName = dataManager().preferences().getUser()?.getUsername() ?: ""
            Logger.debug("31_PREINICIO", userName)

            WorkerApp.initWorker31PreInicio(uniqueId, token, userName)

            return@flatMap Observable.just(true)
        }
    }

    override fun getTokenFirebase() : Observable<Task<InstanceIdResult>> {
        return Observable.create { subscriber ->
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
                if (!subscriber.isDisposed) {
                    subscriber.onNext(task)
                    subscriber.onComplete()
                }
            }
        }
    }

    override fun isTutorialFinish(): Boolean {
        return dataManager().preferences().isTutorialFinish()
    }

    /*override fun isTutorialFinish(): Observable<Boolean> {
        return Observable.fromCallable {
            return@fromCallable dataManager().preferences().isTutorialFinish()
        }
    }*/
}