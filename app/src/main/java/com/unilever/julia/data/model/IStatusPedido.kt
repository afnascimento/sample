package com.unilever.julia.data.model

import com.unilever.julia.data.enums.StatusCardEnum

interface IStatusPedido {

    fun getType(): StatusCardEnum
}