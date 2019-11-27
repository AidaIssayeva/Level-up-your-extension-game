package com.aida.levelupyourextensiongame

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


fun SpannableStringBuilder.lineBreak() {
    append("\n")
}

fun SpannableStringBuilder.space() {
    append(" ")
}

/**
 * Modifying variables of a class
 * in extension functions are not good idea,
 * make regular member function instead
 */
fun MainActivity.showWhiteLoadingIndicator() {
    mNumActiveLoadingIndicators++

    runOnUiThread {
        if (progressBar != null && progressBar!!.visibility != View.VISIBLE) {
            //lottieView!!.setAnimation("white_loader.json") <-some lottie animation
            progressBar.visibility = View.VISIBLE
        }
    }
}

/**
 * Just because you're converting util method, where you pass context to,
 * doesn't mean you should extend context
 */
fun Context.showGif(imageView: ImageView, url: String) {
    Glide.with(this)
        .asGif()
        .listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<GifDrawable>?,
                isFirstResource: Boolean
            ) = false

            override fun onResourceReady(
                resource: GifDrawable?,
                model: Any?,
                target: Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource?.setLoopCount(1)
                return false
            }
        })
        .load(url)
        .into(imageView)
}

/**
 * Just because you're converting util method, where you pass context to,
 * doesn't mean you should extend context
 */
fun Context.showDialog(message: String, activity: Activity?) {
    if (activity != null && !activity.isFinishing) {
        MaterialDialog.Builder(this)
            .content(message)
            .positiveText("Ok")
            .show()
    }
}

fun ImageView.asGif(url: String) {
    Glide.with(this.context)
        .asGif()
        .listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<GifDrawable>?,
                isFirstResource: Boolean
            ) = false

            override fun onResourceReady(
                resource: GifDrawable?,
                model: Any?,
                target: Target<GifDrawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                resource?.setLoopCount(1)
                return false
            }
        })
        .load(url)
        .into(this)
}

fun Activity.showDialog(message: String) {
    if (!isFinishing) {
        MaterialDialog.Builder(this)
            .content(message)
            .positiveText("Ok")
            .show()
    }
}

/**
 * getting color and catching error,
 * although I'm pretty sure in core-ktx there is an extension
 * to retrieve a color from xml
 */
fun Context.getColorRes(resId: Int): Int {
    return try {
        ContextCompat.getColor(this, resId)
    } catch (e: Resources.NotFoundException) {
        ContextCompat.getColor(this, R.color.colorPrimary)
    }

}

