package com.unilever.julia.data.model.notificacao

enum class Type {
    NOT_FOUND, CONTENT_TOP, IMAGE, VIDEO, PDF, LINK_EXTERNO, LINK_INTERNO;

    companion object {
        fun getByName(name: String): Type? {
            if (name.isEmpty()) {
                return null
            }
            for (it in values()) {
                if (it.name == name) {
                    return it
                }
            }
            return null
        }
    }
}