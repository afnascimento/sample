package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class ChatMessageInicioModel(var username: String, var banner: String, var visible: Boolean) : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.CHAT_MESSAGE_INICIO
    }
}