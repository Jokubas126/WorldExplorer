package com.example.worldexplorer.util

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.worldexplorer.R
import com.example.worldexplorer.model.data.Currency
import com.example.worldexplorer.model.data.Language
import com.example.worldexplorer.model.data.RegionalBloc
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou


// ---------------- Image related ------------------//


@BindingAdapter("android:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    GlideToVectorYou
        .init()
        .with(imageView.context)
        .requestBuilder
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions().centerCrop())
        .into(imageView)
}



/**
 *  CODE FOR GETTING BITMAP FROM THE SVG
 *
 * @BindingAdapter("android:imageSvg")
fun loadSvgImage(imageView: ImageView, url: String?) {
    if (url != null)
        imageLoader(url, object : ImageStreamListener {
            override fun onStreamFinished(bitmap: Bitmap) {
                imageView.setImageBitmap(bitmap)
            }
        })
}

private fun imageLoader(url: String, listener: ImageStreamListener) {
    val handler = Handler(Looper.getMainLooper())
    val thread = Thread {
        val input = URL(url).openConnection()
        val svg = SVG.getFromInputStream(input.getInputStream())
        val bitmap = Bitmap.createBitmap(
            ceil(svg.documentWidth.toDouble()).toInt(),
            ceil(svg.documentHeight.toDouble()).toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        svg.renderToCanvas(canvas)
        handler.post { listener.onStreamFinished(bitmap) }
    }
    thread.start()
}

private interface ImageStreamListener {
    fun onStreamFinished(bitmap: Bitmap)
}**/

// --------------- String related ------------ //

@BindingAdapter("android:anyListToString")
fun anyListToString(textView: TextView, list: List<*>?) {
    if (list != null) {
        val stringList = mutableListOf<String>()
        for (value in list)
            when (value) {
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
        if (stringBuilder.isNotBlank())
            stringBuilder.append(", ").append(word)
        else stringBuilder.append(word)
    }
    if (stringBuilder.isNotBlank())
        textView.text = stringBuilder.toString()
    else textView.text = textView.resources.getString(R.string.none_message)
}