package com.unilever.julia.googleTink

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager

class PreferencesTinkImpl(context: Context) : AbstractGoogleTink(), PreferencesTink {

    companion object {
        private val EMPTY_ASSOCIATED_DATA = ByteArray(0)

        private const val PREF_FILE_NAME = "cryptography_pref"
        private const val TINK_KEYSET_NAME = "cryptography_keyset"
        private const val MASTER_KEY_URI = "android-keystore://cryptography_master_key"
    }

    private val mAead : Aead
    init {
        mAead = AndroidKeysetManager.Builder()
            .withSharedPref(context, TINK_KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .keysetHandle
            .getPrimitive(Aead::class.java)
    }

    override fun encrypt(tex: String) : String {
        return encrypt(mAead, tex, EMPTY_ASSOCIATED_DATA)
    }

    override fun decrypt(text: String) : String {
        return decrypt(mAead, text, EMPTY_ASSOCIATED_DATA)
    }
}