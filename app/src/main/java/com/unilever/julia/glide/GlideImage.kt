package com.unilever.julia.glide

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.unilever.julia.R

object GlideImage {

    private var placeHolder : Drawable? = null
    private var imageBroken : Drawable? = null
    private var imageNotFound : Drawable? = null

    fun init(context: Context) {
        //val tintColor = ContextCompat.getColor(context, R.color.colorGray)

        placeHolder = ContextCompat.getDrawable(context, R.drawable.ic_image_loading)//R.drawable.ic_image_place_holder)
        //DrawableCompat.setTint(placeHolder!!, tintColor)

        imageBroken = ContextCompat.getDrawable(context, R.drawable.ic_image_error)//R.drawable.ic_image_broken)
        //DrawableCompat.setTint(imageBroken!!, tintColor)

        imageNotFound = ContextCompat.getDrawable(context, R.drawable.ic_image_error)//R.drawable.ic_image_not_found)
        //DrawableCompat.setTint(imageNotFound!!, tintColor)
    }

    private val mTransition = DrawableTransitionOptions.withCrossFade(500)

    fun into(context: Context, url: String, imageView: ImageView, @DrawableRes placeholder : Int) {
        GlideApp
            .with(context)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .fallback(placeholder)
            .transition(mTransition)
            .into(imageView)
    }

    fun into(context: Context, url: String, imageView: ImageView) {
        GlideApp
            .with(context)
            .load(url)
            .apply(getRequestOptions())
            .transition(mTransition)
            .into(imageView)
    }

    fun into(fragment: Fragment, url: String, imageView: ImageView) {
        GlideApp
            .with(fragment)
            .load(url)
            .apply(getRequestOptions())
            .transition(mTransition)
            .into(imageView)
    }

    fun into(activity: Activity, url: String, imageView: ImageView) {
        GlideApp
            .with(activity)
            .load(url)
            .apply(getRequestOptions())
            .transition(mTransition)
            .into(imageView)
    }

    private fun getRequestOptions(): RequestOptions {
        return RequestOptions()
            .fitCenter()
            .placeholder(placeHolder)
            .error(imageBroken)
            .fallback(imageNotFound)
    }
}