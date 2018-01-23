package com.imnotout.kandyv8hook.NetworkIO

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.annotation .GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
class AppImageModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}

inline fun loadImage(ctx: Context, imgPath: String, view: ImageView, placeHolder: Int? = null, error: Int? = null): Unit {
    val imgRequest: GlideRequest<Drawable> = GlideApp.with(ctx)
            .load(imgPath)
            .dontAnimate()
    placeHolder?.run { imgRequest.placeholder(this) }
    error?.run { imgRequest.placeholder(this) }
    imgRequest.into(view)
}