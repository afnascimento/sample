package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import com.airbnb.lottie.LottieAnimationView

class JuliaSpeechLoad : RelativeLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private var imgAvatar : ImageView

    private var animationView : LottieAnimationView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.julia_loading, this)
        imgAvatar = findViewById(R.id.imgAvatar)
        animationView = findViewById(R.id.lottieAnimation)
    }
}