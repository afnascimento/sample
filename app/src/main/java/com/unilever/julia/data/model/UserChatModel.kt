package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class UserChatModel(var text: String) : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.USER_CHAT
    }
}