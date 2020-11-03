package com.unilever.julia.components.model

import com.unilever.julia.utils.Utils
import org.apache.commons.lang3.StringUtils

data class IpvManagementCard(
    var code: String = "",
    var name: String = "",
    var role: Role = Role.COORDENADOR,
    var meta: Item = Item(),
    var score: Item = Item(),
    var context: Context? = null,
    var items : List<Item> = emptyList()
) {

    fun getItemsSorted(): List<Item> {
        val sortedBy = items.sortedBy { it.percentage }
        return sortedBy
    }

    fun getMetaText(): String {
        return Utils.getTextPercent(meta.percentage)
    }

    fun getMetaColor(): String {
        return meta.colorCode
    }

    fun getScoreText(): String {
        return Utils.getTextPercent(score.percentage)
    }

    fun getScoreColor(): String {
        return score.colorCode
    }

    fun getCodeText(): String {
        return role.prefix +" "+code
    }

    data class Item(
        var percentage: Double = 0.0,
        var colorCode: String = "",
        var description: String = ""
    )

    data class Context(
        var code: String = "",
        var value: String = ""
    ) {
        fun isManagement(): Boolean {
            return StringUtils.equalsIgnoreCase(code, "14_IPV_LISTA_NIVEL")
        }
    }

    enum class Role(var id: String, var prefix: String, var sufix: String) {
        DIRETOR("DIRETOR","Regional","regionais"),
        GERENTE("GERENTE","Filial", "distritos"),
        COORDENADOR("COORDENADOR","Distrito","territórios");
    }

    companion object {
        fun getRole(id: String) : Role {
            if (id.isNotEmpty()) {
                val filter = Role.values().filter { StringUtils.equalsIgnoreCase(it.id, id) }
                if (filter.isNotEmpty()) {
                    return filter[0]
                }
            }
            return Role.COORDENADOR
        }
    }
}