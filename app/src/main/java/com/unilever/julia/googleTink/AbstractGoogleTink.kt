package com.unilever.julia.googleTink

import android.content.Context
import android.util.Base64
import androidx.annotation.RawRes
import com.google.crypto.tink.Aead
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.nio.charset.StandardCharsets

abstract class AbstractGoogleTink {

    private val charset = StandardCharsets.UTF_8

    protected fun getStream(context : Context, @RawRes resId: Int): InputStream {
        return context.resources.openRawResource(resId)
    }

    protected fun getStreamData(context : Context, @RawRes resId: Int): String {
        return IOUtils.toString(getStream(context, resId), charset)
    }

    protected fun encrypt(aead : Aead, text: String, key : String) : String {
        return encrypt(aead, text, key.toByteArray(charset))
    }

    protected fun encrypt(aead : Aead, text: String, key : ByteArray) : String {
        val plaintext = text.toByteArray(charset)
        val ciphertext = aead.encrypt(plaintext, key)
        return base64Encode(ciphertext)
    }

    protected fun decrypt(aead : Aead, text: String, key : String) : String {
        return decrypt(aead, text, key.toByteArray(charset))
    }

    protected fun decrypt(aead : Aead, text: String, key : ByteArray) : String {
        val ciphertext = base64Decode(text)
        val plaintext = aead.decrypt(ciphertext, key)
        return String(plaintext, charset)
    }

    protected fun base64Encode(input: ByteArray): String {
        return Base64.encodeToString(input, Base64.DEFAULT)
    }

    protected fun base64Decode(input: String): ByteArray {
        return Base64.decode(input, Base64.DEFAULT)
    }
}