package com.friendly.helpers

import android.content.Context
import android.util.Log
import coil3.imageLoader
import coil3.memory.MemoryCache

actual class CacheHelper(private val context: Context) {
    actual fun clearFromCacheByKey(key: String) {
        val diskCache = context.imageLoader.diskCache
        val memoryCache = context.imageLoader.memoryCache

        if (diskCache == null) {
            Log.w("CacheHelper", "Disk cache is null, cannot remove key: $key")
        } else {
            diskCache.remove(key)
        }

        if (memoryCache == null) {
            Log.w("CacheHelper", "Memory cache is null, cannot remove key: $key")
        } else {
            memoryCache.remove(MemoryCache.Key(key))
        }
    }
}