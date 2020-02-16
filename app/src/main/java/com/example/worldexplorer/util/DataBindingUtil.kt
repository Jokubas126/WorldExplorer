package com.example.worldexplorer.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.worldexplorer.model.data.Currency
import com.example.worldexplorer.model.data.Language
import com.example.worldexplorer.model.data.RegionalBloc
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

// ---------------- Image related ------------------//

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

// --------------- String related ------------ //

@BindingAdapter("android:anyListToString")
fun anyListToString(textView: TextView, list: List<*>?) {
    if (list != null) {
        val stringList = mutableListOf<String>()
        for (value in list)
            when (value){
                is Currency -> stringList.add(value.name)
                is Language -> stringList.add(value.name)
                is RegionalBloc -> stringList.add(value.name)
            }
        listToString(textView, stringList)
    }
}

private fun listToString(textView: TextView, list: List<String>) {
    val stringBuilder: StringBuilder = java.lang.StringBuilder()
    for (word in list) {
        if (!stringBuilder.isBlank())
            stringBuilder.append(", ").append(word)
        else stringBuilder.append(word)
    }
    textView.text = stringBuilder.toString()
}