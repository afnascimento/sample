package com.unilever.julia.worker

import androidx.work.*

object WorkerApp {

    fun initWorker31PreInicio(imeiDevice: String, tokenFirebase: String, userName: String) {
        val inputData = Data.Builder()
            .putString(Worker31PreInicio.tokenFirebase, tokenFirebase)
            .putString(Worker31PreInicio.imeiDevice, imeiDevice)
            .putString(Worker31PreInicio.nameUser, userName)
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val otwRequest = OneTimeWorkRequest.Builder(Worker31PreInicio::class.java)
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueue(otwRequest)
    }
}