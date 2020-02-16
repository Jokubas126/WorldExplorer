package com.example.worldexplorer.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    val requestBuilder = GlideToVectorYou
        .init()
        .with(imageView.context)
        .requestBuilder


    requestBuilder
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions.centerCropTransform())
        .into(imageView)
}