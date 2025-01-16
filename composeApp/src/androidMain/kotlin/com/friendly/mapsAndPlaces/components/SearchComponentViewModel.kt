package com.friendly.mapsAndPlaces.components

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.mapsAndPlaces.IPlacesClientProvider
import com.friendly.mapsAndPlaces.LocationAndGeocodingHelper
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchComponentViewModel() : ViewModel(), KoinComponent {

    private val placesClientProvider: IPlacesClientProvider by inject()

    val coordinates = mutableStateOf(LatLng(0.0, 0.0))

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    private val _isLocationSelected = MutableStateFlow(false)
    val isLocationSelected: StateFlow<Boolean> = _isLocationSelected

    fun locationIsSelected(newValue: Boolean){
        _isLocationSelected.value = newValue
    }

    fun onQueryChanged(newQuery: String, context: Context) {
        _query.value = newQuery
        if(_isLocationSelected.value.not()) {
            fetchSuggestions(newQuery, context)
        }
    }

    private fun fetchSuggestions(query: String, context: Context) {
        if (query.isBlank()) {
            _suggestions.value = emptyList()
            return
        }

        viewModelScope.launch {
            val suggestions = fetchPlacesFromGoogle(query, context)
            _suggestions.value = suggestions
        }
    }

    private suspend fun fetchPlacesFromGoogle(query: String, context: Context): List<String> {

        val placesClient = placesClientProvider.getPlacesClient(context)

        LocationAndGeocodingHelper.getLastLocation(
            context,
            onPermissionDenied = { }) { newCoordinates ->
            coordinates.value = newCoordinates
        }

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
