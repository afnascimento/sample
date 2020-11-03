package com.unilever.julia.data.model

data class InitDataChatMain(
    var isTextToSpeech: Boolean,
    var user: User,
    var countConfigVoice: Int,
    var banner : String,
    var bannerVisible : Boolean
)