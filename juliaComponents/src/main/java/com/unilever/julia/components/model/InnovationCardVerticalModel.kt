package com.unilever.julia.components.model

import com.unilever.julia.components.enums.ComponentsType
import com.unilever.julia.components.ButtonInnovationVertical

class InnovationCardVerticalModel : InnovationCardModel() {

    override fun getType(): ComponentsType {
        return ComponentsType.INNOVATION_CARD_VERTICAL
    }

    var items : MutableList<Item> = arrayListOf()

    data class Item(var card : Card, var reference: ContentReference) : ButtonInnovationVertical.Item {

        override fun icon(): String {
            return card.icon
        }

        override fun icon2(): String {
            return card.icon2
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