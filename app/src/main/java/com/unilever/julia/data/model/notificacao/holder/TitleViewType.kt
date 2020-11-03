package com.unilever.julia.data.model.notificacao.holder

data class TitleViewType(
    var title: String = ""
) : NotificationViewType {

    override fun getType(): NotificationViewType.Type {
        return NotificationViewType.Type.TITLE
    }
}