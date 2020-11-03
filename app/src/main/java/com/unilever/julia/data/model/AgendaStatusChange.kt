package com.unilever.julia.data.model

import com.google.gson.annotations.SerializedName

class AgendaStatusChange {

    @SerializedName("code")
    var code: String = ""

    @SerializedName("context")
    var context: Context = Context()

    class Context {

        @SerializedName("id_task")
        var idTask = ""

        @SerializedName("data_task")
        var dataTask = ""

        @SerializedName("status_task")
        var statusTask = ""

        constructor()

        constructor(idTask: String, dataTask: String, statusTask: String) {
            this.idTask = idTask
            this.dataTask = dataTask
            this.statusTask = statusTask
        }
    }
}
