package com.unilever.julia.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.unilever.julia.data.model.User
import org.apache.commons.lang3.StringUtils
import com.google.gson.GsonBuilder
import com.unilever.julia.BuildConfig
import com.unilever.julia.googleTink.PreferencesTink
import com.unilever.julia.data.model.Banner
import com.unilever.julia.data.model.Token
import com.unilever.julia.firebase.FirebaseAnalyticsApp
import com.unilever.julia.firebase.parser.FirebaseNotification

class PreferencesHelperImpl(context: Context, private val mPreferencesTink: PreferencesTink) : PreferencesHelper {

    companion object {
        private const val PREFS_NAME = "julia_preferences"

        private const val PREF_TEXT_TO_SPEECH = "PREF_TEXT_TO_SPEECH"
        private const val PREF_OUTLOOK = "PREF_OUTLOOK"
        private const val PREF_IPV = "PREF_IPV"

        private const val PREF_USER = "PREF_USER"
        private const val PREF_TOKEN = "PREF_TOKEN"

        private const val PREF_BANNER = "PREF_BANNER"

        private const val PREF_PUSH_NOT_READ = "PREF_PUSH_NOT_READ"
    }

    private val mPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val mGson = GsonBuilder().create()

    override fun getEventCount(event: FirebaseAnalyticsApp.Events): Int {
        return  mPreferences.getInt(event.name, 0)
    }

    override fun addEventCount(event: FirebaseAnalyticsApp.Events) {
        val count = (mPreferences.getInt(event.name, 0) + 1)
        val editor = mPreferences.edit()
        editor.putInt(event.name, count)
        editor.apply()
    }

    private fun getNamePreferenceTutorial(): String {
        return "PREF_TUTORIAL_"+ BuildConfig.VERSION_NAME
    }

    override fun isTutorialFinish(): Boolean {
        return mPreferences.getBoolean(getNamePreferenceTutorial(), false)
    }

    override fun saveTutorialFinish() {
        val editor = mPreferences.edit()
        editor.putBoolean(getNamePreferenceTutorial(), true)
        editor.apply()
    }

    override fun isTextToSpeech(): Boolean {
        return mPreferences.getBoolean(PREF_TEXT_TO_SPEECH, true)
    }

    override fun saveTextToSpeech(enabled: Boolean) {
        val editor = mPreferences.edit()
        editor.putBoolean(PREF_TEXT_TO_SPEECH, enabled)
        editor.apply()
    }

    override fun isOutlook(): Boolean {
        return mPreferences.getBoolean(PREF_OUTLOOK, true)
    }

    override fun saveOutlook(enabled: Boolean) {
        val editor = mPreferences.edit()
        editor.putBoolean(PREF_OUTLOOK, enabled)
        editor.apply()
    }

    override fun isIPV(): Boolean {
        return mPreferences.getBoolean(PREF_IPV, true)
    }

    override fun saveIPV(enabled: Boolean) {
        val editor = mPreferences.edit()
        editor.putBoolean(PREF_IPV, enabled)
        editor.apply()
    }

    private fun getEncryptText(encryptText : String) : String {
        return mPreferencesTink.encrypt(encryptText)
    }

    private fun getDecryptText(decryptText : String?) : String {
        if (decryptText != null && decryptText.isNotEmpty()) {
            return mPreferencesTink.decrypt(decryptText)
        }
        return ""
    }

    override fun getToken(): Token? {
        val json = getDecryptText(mPreferences.getString(PREF_TOKEN, ""))
        if (StringUtils.isNotEmpty(json)) {
            return mGson.fromJson(json, Token::class.java)
        }
        return null
    }

    override fun saveToken(token: Token) {
        val editor = mPreferences.edit()
        editor.putString(PREF_TOKEN, getEncryptText(mGson.toJson(token)))
        editor.apply()
        editor.commit()
    }

    override fun getUser(): User? {
        val json = getDecryptText(mPreferences.getString(PREF_USER, ""))
        if (StringUtils.isNotEmpty(json)) {
            return mGson.fromJson(json, User::class.java)
        }
        return null
    }

    override fun saveUser(user: User) {
        val editor = mPreferences.edit()
        editor.putString(PREF_USER, getEncryptText(mGson.toJson(user)))
        editor.apply()
        editor.commit()
    }

    override fun saveBanner(banner: Banner) {
        val editor = mPreferences.edit()
        editor.putString(PREF_BANNER, getEncryptText(mGson.toJson(banner)))
        editor.apply()
        editor.commit()
    }

    override fun getBanner(): Banner? {
        val json = getDecryptText(mPreferences.getString(PREF_BANNER, ""))
        if (StringUtils.isNotEmpty(json)) {
            return mGson.fromJson(json, Banner::class.java)
        }
        return null
    }

    override fun updateReadPushMessage(notification: FirebaseNotification) {
        if (notification.getId().isEmpty()) {
            return
        }
        val mutableSet = getPushMessagesNotReadsInPreferences()
        if (!mutableSet.contains(notification.getId())) {
            return
        }
        mutableSet.remove(notification.getId())
        savePushMessageNotReads(mutableSet)
    }

    override fun savePushMessageNotReads(notification: FirebaseNotification) {
        if (notification.getId().isEmpty()) {
            return
        }
        if (!(notification.getType() == FirebaseNotification.Type.CODE)) {
            return
        }
        val mutableSet = getPushMessagesNotReadsInPreferences()
        if (mutableSet.contains(notification.getId())) {
            return
        }
        mutableSet.add(notification.getId())
        savePushMessageNotReads(mutableSet)
    }

    override fun savePushMessageNotReads(idsNotReads : Set<String>) {
        val editor = mPreferences.edit()
        editor.putStringSet(PREF_PUSH_NOT_READ, idsNotReads)
        editor.apply()
        editor.commit()
    }

    override fun getPushMessagesNotReads(notification: FirebaseNotification): Int {
        savePushMessageNotReads(notification)
        val notReads = getPushMessagesNotReadsInPreferences()
        return notReads.size
    }

    override fun getPushMessagesNotReads(): Int {
        val notReads = getPushMessagesNotReadsInPreferences()
        return notReads.size
    }

    private fun getPushMessagesNotReadsInPreferences(): MutableSet<String> {
        return mPreferences.getStringSet(PREF_PUSH_NOT_READ, mutableSetOf()) ?: mutableSetOf()
    }
}