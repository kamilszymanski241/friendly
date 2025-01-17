package com.friendly.helpers

expect class LocationAndGeocodingHelper {
    suspend fun getLastLocation(
        onPermissionDenied: () -> Unit,
        onLocationRetrieved: (Pair<Double,Double>) -> Unit
    )

    suspend fun getLatLngFromPlace(
        place: String,
        onLatLngFetched: (Pair<Double,Double>) -> Unit
    )

    suspend fun fetchAddress(
        latLng: Pair<Double, Double>,
        onAddressFetched: (String) -> Unit
    )
}