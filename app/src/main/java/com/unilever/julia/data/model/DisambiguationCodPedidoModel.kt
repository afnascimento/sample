package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class DisambiguationCodPedidoModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.DISAMBIGUATION_COD_PEDIDO
    }

    var text : String = ""
}