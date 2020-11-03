package com.unilever.julia.data.model.notificacao.holder

interface NotificationViewType {

    fun getType(): Type

    enum class Type {
        TITLE, CARD
    }
}