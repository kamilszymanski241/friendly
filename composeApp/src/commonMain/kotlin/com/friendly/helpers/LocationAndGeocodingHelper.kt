package com.friendly.helpers

expect class LocationAndGeocodingHelper {
    fun getLastLocation(
        onPermissionDenied: () -> Unit,
        onLocationRetrieved: (Pair<Double,Double>) -> Unit
    )

    fun getLatLngFromPlace(
        place: String,
        onLatLngFetched: (Pair<Double,Double>) -> Unit
    )

    fun fetchAddress(
        latLng: Pair<Double, Double>,
        onAddressFetched: (String) -> Unit
    )
}