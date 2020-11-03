package com.unilever.julia.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class CardViewImage : CoordinatedBaseView  {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    private val imgProduct: ImageView

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        inflate(context, R.layout.card_view_image, this)
        imgProduct = findViewById(R.id.imgProduct)
    }

    fun getImageView(): ImageView {
        return imgProduct
    }
}