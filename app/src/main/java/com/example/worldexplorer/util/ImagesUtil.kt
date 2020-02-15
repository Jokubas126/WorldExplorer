package com.example.worldexplorer.util

import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String) {
    val requestBuilder: RequestBuilder<PictureDrawable> = GlideToVectorYou
        .init()
        .with(view.context)
        .requestBuilder

    requestBuilder
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions.centerCropTransform())
        .into(view)
}