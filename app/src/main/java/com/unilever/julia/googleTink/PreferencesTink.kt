package com.unilever.julia.googleTink

interface PreferencesTink {
    fun encrypt(tex: String): String
    fun decrypt(text: String): String
}