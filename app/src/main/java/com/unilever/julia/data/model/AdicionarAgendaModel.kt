package com.unilever.julia.data.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.model.IComponentsModel

class AdicionarAgendaModel : IComponentsModel {

    override fun getType(): ComponentsType {
        return ComponentsType.ADICIONAR_AGENDA
    }

    var text : String = ""

    var items : MutableList<Item> = arrayListOf()

    class Item {
        var title : String = ""

        var textAction : String = ""

        var action : Boolean = false

        var assunto : String = ""

        var data : String = ""
    }
}