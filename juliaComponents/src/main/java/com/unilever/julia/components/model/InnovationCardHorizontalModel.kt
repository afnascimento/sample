package com.unilever.julia.components.model

import com.unilever.julia.components.ButtonInnovationHorizontal
import com.unilever.julia.components.enums.ComponentsType

class InnovationCardHorizontalModel : InnovationCardModel() {

    override fun getType(): ComponentsType {
        return ComponentsType.INNOVATION_CARD_HORIZONTAL
    }

    var items : MutableList<Item> = arrayListOf()

    data class Item(var card : Card, var reference: ContentReference) : ButtonInnovationHorizontal.Item {

        override fun icon(): String {
            return card.icon
        }

        override fun color(): String {
            return card.color
        }

        override fun text(): String {
            return card.text
        }

        override fun projects(): Int {
            return card.projects
        }
    }
}