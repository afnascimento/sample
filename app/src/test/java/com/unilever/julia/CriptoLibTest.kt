package com.unilever.julia

import com.google.gson.Gson
import com.unilever.julia.cripto.AESCrypt
import com.unilever.julia.cripto.UnitTestBase64
import com.unilever.julia.data.model.Enviroment
import org.apache.commons.io.IOUtils
import org.davidmoten.text.utils.WordWrap
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import java.io.InputStream
import java.nio.charset.StandardCharsets

class CriptoLibTest {

    companion object {
        @JvmStatic
        private lateinit var mGson: Gson

        @JvmStatic
        private lateinit var mAesCrypt: AESCrypt

        @BeforeClass
        @JvmStatic
        fun initialize() {
            mGson = Gson()
            mAesCrypt = AESCrypt(UnitTestBase64())
        }
    }

    enum class TypeConfig(var file: String) {
        DEV("dev_config.json"),
        SCAN("appscan_config.json"),
        PROD("prod_config.json"),
        PRE_PROD("preprod_config.json")
    }

    private fun getJson(type: TypeConfig): String {
        val classLoader : ClassLoader = this.javaClass.classLoader ?: return ""
        val input : InputStream = classLoader.getResourceAsStream(type.file)
        return IOUtils.toString(input, StandardCharsets.UTF_8)
    }

    @Test
    fun generate_decrypt_test() {
        val json = getJson(TypeConfig.DEV)

        val encrypt = mAesCrypt.encrypt(json)
        val wrap = WordWrap.from(encrypt).maxWidth(80).wrap()
        println(wrap)

        val decrypt = mAesCrypt.decrypt(encrypt)
        //println(decrypt)
        val enviroment = mGson.fromJson(decrypt, Enviroment::class.java)

        Assert.assertThat(enviroment, notNullValue())
    }

    @Ignore
    @Test
    fun backend_decrypt_test() {
        val decrypt = mAesCrypt.decrypt("G/bqFwday2q+yuZMbEkzS4tz8PczLgFRUjcAWAoMc1c=")

        Assert.assertThat(decrypt, `is`("this is my plain text"))
    }
}