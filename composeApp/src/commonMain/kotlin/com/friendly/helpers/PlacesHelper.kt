package com.friendly.helpers

expect class PlacesHelper {
    suspend fun fetchPlacesFromGoogle(query: String): List<String>
}