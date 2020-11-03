package com.unilever.julia.data.enums

enum class StatusCardEnum(private var id : Int) {
    CARD(1), ITEM_TOP(2), ITEM_MIDDLE(3), ITEM_BOTTOM(4);

    fun getId(): Int {
        return id
    }
}