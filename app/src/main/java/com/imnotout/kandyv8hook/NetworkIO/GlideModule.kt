package com.imnotout.kandyv8hook.NetworkIO

import com.bumptech.glide.annotation .GlideModule;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
class AppImageModule : AppGlideModule() {
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}
