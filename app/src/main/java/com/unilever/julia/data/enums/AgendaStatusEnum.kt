package com.unilever.julia.data.enums

enum class AgendaStatusEnum(var code: String, var statusTask: String) {

    CONCLUIR("19_AGENDA_FINALIZAR", "Completed"),
    CANCELAR("20_AGENDA_CANCELAR", "Cancelled");
}