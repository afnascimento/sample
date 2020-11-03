package com.unilever.julia.data.preferences

import com.unilever.julia.data.model.Banner
import com.unilever.julia.data.model.Token
import com.unilever.julia.data.model.User
import com.unilever.julia.firebase.FirebaseAnalyticsApp
import com.unilever.julia.firebase.parser.FirebaseNotification

interface PreferencesHelper {

    fun saveTextToSpeech(enabled: Boolean)
    fun isTextToSpeech(): Boolean
    fun saveOutlook(enabled: Boolean)
    fun isOutlook(): Boolean
    fun saveIPV(enabled: Boolean)
    fun isIPV(): Boolean
    fun getUser(): User?
    fun saveUser(user: User)
    fun getToken(): Token?
    fun saveToken(token: Token)
    fun isTutorialFinish(): Boolean
    fun saveTutorialFinish()
    fun getEventCount(event: FirebaseAnalyticsApp.Events): Int
    fun addEventCount(event: FirebaseAnalyticsApp.Events)
    fun saveBanner(banner: Banner)
    fun getBanner(): Banner?
    fun savePushMessageNotReads(notification: FirebaseNotification)
    fun getPushMessagesNotReads(notification: FirebaseNotification): Int
    fun getPushMessagesNotReads(): Int
    fun updateReadPushMessage(notification: FirebaseNotification)
    fun savePushMessageNotReads(idsNotReads: Set<String>)
}
