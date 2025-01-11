package com.friendly

import android.content.Context
import com.google.android.libraries.places.api.net.PlacesClient

interface IPlacesClientProvider {
    fun getPlacesClient(context: Context): PlacesClient
    fun shutdown()
}