package com.unilever.julia.ui.inovacao.detailV2.model

data class PairFragment(val title: String? = null, val type: Type) {

    enum class Type {
        Resumo,
        Execucao,
        NovosSkus,
        SkusDeslistados,
        Visibilidade
    }

    class Builder {
        private var items = arrayListOf<PairFragment>()

        fun addItem(title : String?, type : Type): Builder {
            addItem(title, true, type)
            return this
        }

        fun addItem(title : String?, isValid: Boolean, type : Type): Builder {
            if (title != null && isValid) {
                items.add(PairFragment(title, type))
            }
            return this
        }

        fun create(): MutableList<PairFragment> {
            return items
        }
    }
}