package com.unilever.julia.components.boletim

class AttendancesModel(
    var territory : List<AttendanceModel> = emptyList(),
    var district : List<AttendanceModel> = emptyList(),
    var hitRegional : List<AttendanceModel> = emptyList(),
    var subsidiary : List<AttendanceModel> = emptyList(),
    var selected : AttendanceModel.Type? = null
) {
    fun getSelected(): AttendanceModel {
        return when (selected) {
            AttendanceModel.Type.territory -> {
                territory[0]
            }
            AttendanceModel.Type.district -> {
                district[0]
            }
            AttendanceModel.Type.hitRegional -> {
                hitRegional[0]
            }
            AttendanceModel.Type.subsidiary -> {
                subsidiary[0]
            }
            else -> AttendanceModel()
        }
    }
}