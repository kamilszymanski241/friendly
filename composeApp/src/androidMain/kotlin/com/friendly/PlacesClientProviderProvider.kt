package com.friendly

import Friendly.composeApp.BuildConfig
import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class PlacesClientProvider: IPlacesClientProvider {

    private var placesClientProvider: PlacesClient? = null

    override fun getPlacesClient(context: Context): PlacesClient {
        if (placesClientProvider == null) {
            Places.initialize(context.applicationContext, BuildConfig.GOOGLE_MAPS_ANDROID_API_KEY)
            placesClientProvider = Places.createClient(context.applicationContext)
        }
        return placesClientProvider!!
    }

    override fun shutdown() {
        placesClientProvider = null // Pozwala na późniejsze zwolnienie zasobów przez GC
    }
}