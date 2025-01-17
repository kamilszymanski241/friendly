package com.friendly.helpers

import Friendly.composeApp.BuildConfig
import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class PlacesClientProvider( private val context: Context ) {

    private var placesClientProvider: PlacesClient? = null

    fun getPlacesClient(): PlacesClient {
        if (placesClientProvider == null) {
            Places.initialize(context.applicationContext, BuildConfig.GOOGLE_MAPS_ANDROID_API_KEY)
            placesClientProvider = Places.createClient(context.applicationContext)
        }
        return placesClientProvider!!
    }

    fun shutdown() {
        placesClientProvider = null
    }
}