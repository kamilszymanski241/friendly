package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.helpers.PlacesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchLocationViewModel: ViewModel(), KoinComponent {

    private val placesHelper: PlacesHelper by inject()

    private val locationAndGeocodingHelper: LocationAndGeocodingHelper by inject()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    private val _isLocationSelected = MutableStateFlow(true)
    val isLocationSelected: StateFlow<Boolean> = _isLocationSelected

    fun locationIsSelected(newValue: Boolean){
        _isLocationSelected.value = newValue
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        if(_isLocationSelected.value.not()) {
            fetchSuggestions(newQuery)
        }
    }

    private fun fetchSuggestions(query: String) {
        if (query.isBlank()) {
            _suggestions.value = emptyList()
            return
        }

        viewModelScope.launch {
            val suggestions = placesHelper.fetchPlacesFromGoogle(query)
            _suggestions.value = suggestions
        }
    }
}