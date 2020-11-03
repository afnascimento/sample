package com.unilever.julia.ui.boletim.area

import com.google.gson.Gson
import com.unilever.julia.components.boletim.AttendanceModel
import com.unilever.julia.components.boletim.AttendancesModel
import com.unilever.julia.ui.base.*

import com.unilever.julia.data.*
import com.unilever.julia.data.api.JuliaIntent
import com.unilever.julia.data.model.Conversations
import com.unilever.julia.data.model.bulletin.AttendanceResponse
import io.reactivex.Observable
import org.apache.commons.lang3.StringUtils

import javax.inject.Inject

class AttendanceInteractorImpl
@Inject constructor(mDataManager: DataManager) : BaseInteractorImpl(mDataManager), AttendanceInteractor {

    private val mGson = Gson()

    override fun getAttendance(myTerritory : String): Observable<AttendancesModel> {
        return dataManager().juliaApi().sendIntent("", JuliaIntent.Intent.BOLETIM_ATTENDANCE)
            .flatMap { conversations: Conversations ->
                val obj = mGson.fromJson(conversations.getAnswer().technicalText, AttendanceResponse::class.java)

                val model = AttendancesModel()

                // territory
                val territoryType = AttendanceModel.Type.territory
                val territoryPair = getList(obj.territory ?: mapOf(), territoryType, myTerritory)
                val territoriesItems = getItems(territoryType, myTerritory, territoryPair)
                if (territoriesItems.first) {
                    model.selected = territoryType
                }
                model.territory = territoriesItems.second

                // district
                val districtType = AttendanceModel.Type.district
                val districtPair = getList(obj.district ?: mapOf(), districtType, myTerritory)
                val districtItems = getItems(districtType, myTerritory, districtPair)
                if (districtItems.first) {
                    model.selected = districtType
                }
                model.district = districtItems.second

                // hitRegional
                val regionalType = AttendanceModel.Type.hitRegional
                val regionalPair = getList(obj.hitRegional ?: emptyList(), regionalType, myTerritory)
                val regionalItems = getItems(regionalType, myTerritory, regionalPair)
                if (regionalItems.first) {
                    model.selected = regionalType
                }
                model.hitRegional = regionalItems.second

                // subsidiary
                val subsidiaryType = AttendanceModel.Type.subsidiary
                val subsidiaryPair = getList(obj.subsidiary ?: emptyList(), subsidiaryType, myTerritory)
                val subsidiaryItems = getItems(subsidiaryType, myTerritory, subsidiaryPair)
                if (subsidiaryItems.first) {
                    model.selected = subsidiaryType
                }
                model.subsidiary = subsidiaryItems.second

                return@flatMap Observable.just(model)
            }
    }

    private fun getItems(type: AttendanceModel.Type,
                         myTerritory: String,
                         pair: Pair<Boolean, List<AttendanceModel>>): Pair<Boolean, List<AttendanceModel>> {
        val containsTerritory = pair.first
        val sortedList = pair.second

        if (!containsTerritory) {
            return Pair(false, sortedList)
        }

        val items = arrayListOf<AttendanceModel>()

        // add first item
        val obj = AttendanceModel()
        obj.id = myTerritory

        var text = ""
        if (type == AttendanceModel.Type.territory) {
            text = "Meu território ($myTerritory)"
        } else if (type == AttendanceModel.Type.district) {
            text = "Meu distrito ($myTerritory)"
        } else if (type == AttendanceModel.Type.hitRegional) {
            text = "Minha regional ($myTerritory)"
        } else if (type == AttendanceModel.Type.subsidiary) {
            text = "Minha filial ($myTerritory)"
        }

        obj.description = text
        obj.textFilter = normalizeTextFilter(text)
        obj.textSorted = text
        obj.type = AttendanceModel.Type.territory
        items.add(obj)

        // add items sorted
        items.addAll(sortedList)

        return Pair(true, items)
    }

    private fun getList(list: List<String>, type: AttendanceModel.Type, key: String): Pair<Boolean, List<AttendanceModel>> {
        val items = arrayListOf<AttendanceModel>()
        var contains = false
        for (it in list) {
            if (StringUtils.equalsIgnoreCase(it, key)) {
                contains = true
            } else {
                val obj = AttendanceModel()
                obj.id = it
                obj.description = it
                obj.textFilter = normalizeTextFilter(it)
                obj.textSorted = it
                obj.type = type
                items.add(obj)
            }
        }
        return Pair(contains, items.sortedBy { it.textSorted })
    }

    private fun getList(map : Map<String, String>, type: AttendanceModel.Type, key: String): Pair<Boolean, List<AttendanceModel>> {
        val list = arrayListOf<AttendanceModel>()
        var containsKey = false
        for (it in map) {
            if (StringUtils.equalsIgnoreCase(it.key, key)) {
                containsKey = true
            } else {
                val obj = AttendanceModel()
                obj.id = it.key
                obj.description = it.key +" - "+ it.value
                obj.textFilter = normalizeTextFilter(it.key +" "+ it.value)
                obj.textSorted = it.value
                obj.type = type
                list.add(obj)
            }
        }
        return Pair(containsKey, list.sortedBy { it.textSorted })
    }

    private fun normalizeTextFilter(text: String): String {
        return StringUtils.stripAccents(StringUtils.upperCase(StringUtils.trim(text)))
    }
}