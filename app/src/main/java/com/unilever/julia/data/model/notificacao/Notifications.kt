package com.unilever.julia.data.model.notificacao

import com.google.gson.annotations.SerializedName

data class Notifications(
    @SerializedName("notifications")
    var notifications: List<Notification>? = listOf()
)