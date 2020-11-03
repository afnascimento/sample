package com.unilever.julia.firebase

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.unilever.julia.data.model.InovacaoProjetoModel
import com.unilever.julia.data.preferences.PreferencesHelper
import com.unilever.julia.utils.capitalizeAllText
import org.apache.commons.lang3.StringUtils

class FirebaseAnalyticsApp(context: Context, preferences: PreferencesHelper) {

    enum class Events(var nameEvent: String) {
        VOICE_SETTING_ENABLED("voice_setting_enabled"),
        ORDER_SHARE("order_share"),
        INNOVATION_DETAILS("innovation_details"),
        INNOVATION_TRADESTORY("innovation_tradestory"),
        PUSH_NOTIFICATION_OPEN("push_notification_open")
    }

    enum class Params(var nameParam: String) {
        ENABLED(FirebaseAnalytics.Param.SUCCESS),
        SHARED(FirebaseAnalytics.Param.SUCCESS),
        NOTIFICATION_CATEGORY(FirebaseAnalytics.Param.ITEM_NAME),

        Projeto_Inovacao("projeto_inovacao"),
        Categoria("categoria"),
        Commodity("commodity"),
        Marca("marca"),
        Canal("canal"),
        Tradestory("tradestory")
    }

    private var mFirebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    private var mPreferences : PreferencesHelper = preferences

    init {
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true)
    }

    private fun parseLong(condition: Boolean): Long {
        return if (condition) 1L else 0L
    }

    fun addEventVoiceSettingEnabled(enabled: Boolean) {
        setPropertiesUserSession()
        val bundle = Bundle()
        bundle.putLong(Params.ENABLED.nameParam, parseLong(enabled))
        mFirebaseAnalytics.logEvent(Events.VOICE_SETTING_ENABLED.nameEvent, bundle)
    }

    fun addEventOrderShared(enabled: Boolean) {
        setPropertiesUserSession()
        val bundle = Bundle()
        bundle.putLong(Params.SHARED.nameParam, parseLong(enabled))
        mFirebaseAnalytics.logEvent(Events.ORDER_SHARE.nameEvent, bundle)
    }

    private fun setAddParamsInnovation(bundle : Bundle, model : InovacaoProjetoModel) {
        bundle.putString(Params.Projeto_Inovacao.nameParam, model.projetoInovacao)
        bundle.putString(Params.Categoria.nameParam, model.categoria)
        bundle.putString(Params.Commodity.nameParam, model.commodity)
        bundle.putString(Params.Marca.nameParam, model.marca)
        bundle.putString(Params.Canal.nameParam, model.canal)
    }

    fun addEventInnovationDetails(model : InovacaoProjetoModel) {
        setPropertiesUserSession()

        val bundle = Bundle()
        setAddParamsInnovation(bundle, model)

        mFirebaseAnalytics.logEvent(Events.INNOVATION_DETAILS.nameEvent, bundle)
    }

    fun addEventInnovationTradestory(model : InovacaoProjetoModel) {
        setPropertiesUserSession()

        val bundle = Bundle()
        bundle.putString(Params.Tradestory.nameParam, model.linkTradeStory)
        setAddParamsInnovation(bundle, model)

        mFirebaseAnalytics.logEvent(Events.INNOVATION_TRADESTORY.nameEvent, bundle)
    }

    fun addEventPushNotificationOpen(category: String) {
        setPropertiesUserSession()

        val bundle = Bundle()
        bundle.putString(Params.NOTIFICATION_CATEGORY.nameParam, category)

        mFirebaseAnalytics.logEvent(Events.PUSH_NOTIFICATION_OPEN.nameEvent, bundle)
    }

    fun addEventParamsOriginBrokerLuis(nameEvent: String, valueEvent: String) {
        setPropertiesUserSession()
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, StringUtils.trim(valueEvent))
        mFirebaseAnalytics.logEvent(StringUtils.trim(nameEvent), bundle)
    }

    private fun setPropertiesUserSession() {
        val user = mPreferences.getUser()
        if (user != null) {
            mFirebaseAnalytics.setUserId(user.mail.capitalizeAllText())
            mFirebaseAnalytics.setUserProperty("username", user.getUsername())
        }
    }
}