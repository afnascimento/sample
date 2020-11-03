package com.unilever.julia.msal

import java.io.File

interface IMsalClientHandler {

    fun msalApplicationId(): String

    fun msalScopes(): Array<String>

    fun msalFileConfig(): File
}