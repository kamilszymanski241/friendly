package com.friendly.helpers

import android.content.Context
import coil3.imageLoader
import coil3.memory.MemoryCache

actual class CacheHelper(private val context: Context) {
    actual fun clearFromCacheByKey(key: String) {
        context.imageLoader.diskCache!!.remove(key)
        context.imageLoader.memoryCache!!.remove(MemoryCache.Key(key))
    }
}