package com.unilever.julia.ui.component

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.unilever.julia.R
import org.apache.commons.lang3.StringUtils

class JuliaHelloMessage : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var tvUsername : TextView

    private var imgBanner : ImageView

    private var imgAvatar : ImageView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_hello_message, this)

        imgBanner = findViewById(R.id.imgBanner)

        imgAvatar = findViewById(R.id.imgAvatar)

        tvUsername = findViewById(R.id.tvUsername)
    }

    fun setUsername(username: String) {
        tvUsername.text = context.getString(R.string.header_hello_username, username)
    }

    fun setBanner(url: String, visible: Boolean) {
        if (!visible) {
            return
        }

        if (url.contains("file", true)) {
            imgBanner.setImageURI(Uri.parse(url))
            imgBanner.visibility = View.VISIBLE
            imgAvatar.visibility = View.GONE
            return
        }

        val cache = getCache(url)
        if (cache != null) {
            imgBanner.setImageResource(cache)
            imgBanner.visibility = View.VISIBLE
            imgAvatar.visibility = View.GONE
            return
        }

        imgBanner.visibility = View.GONE
        imgAvatar.visibility = View.VISIBLE
    }

    private fun getCache(url: String): Int? {
        var temp = url
        val split = StringUtils.split(url, ".")
        if (split.isNotEmpty()) {
            temp = split[0]
        }

        return when (temp) {
            //"banner_1" -> R.drawable.banner_1
            "banner_2" -> R.drawable.banner_2
            //"banner_3" -> R.drawable.banner_3
            //"banner_4" -> R.drawable.banner_4
            //"banner_5" -> R.drawable.banner_5
            else -> null
        }
    }
}