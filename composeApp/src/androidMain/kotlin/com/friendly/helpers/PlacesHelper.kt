package com.friendly.helpers

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class PlacesHelper: KoinComponent {

    private val placesClientProvider: PlacesClientProvider by inject()

    actual suspend fun fetchPlacesFromGoogle(query: String): List<String> {

        val placesClient = placesClientProvider.getPlacesClient()

        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        return try {
            val response = placesClient.findAutocompletePredictions(request).await()

            response.autocompletePredictions.map { prediction ->
                prediction.getFullText(null).toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}