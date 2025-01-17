package com.friendly.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.helpers.DateTimeHelper
import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiscoverScreenViewModel: ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()

    private val locationAndGeocodingHelper: LocationAndGeocodingHelper by inject()

    private val _eventsList = MutableStateFlow<List<Event>?>(null)
    val eventsList: StateFlow<List<Event>?> = _eventsList

    private val _distance = MutableStateFlow(15)
    val distance: StateFlow<Int> = _distance

    private val _tag = MutableStateFlow<List<Int>>(emptyList())
    val tag: StateFlow<List<Int>> = _tag

    private val _selectedLocationCoordinates = MutableStateFlow(Pair(0.0, 0.0))
    val selectedLocationCoordinates: StateFlow<Pair<Double, Double>> = _selectedLocationCoordinates

    private val _selectedLocationAddress = MutableStateFlow("")
    val selectedLocationAddress: StateFlow<String> = _selectedLocationAddress

    fun initialize(){
        if(_eventsList.value == null)
        {
            viewModelScope.launch {
                locationAndGeocodingHelper.getLastLocation(
                    onPermissionDenied = {},
                    onLocationRetrieved = { location->
                        _selectedLocationCoordinates.value=Pair(location.first,location.second)
                        viewModelScope.launch {
                            locationAndGeocodingHelper.fetchAddress(
                                _selectedLocationCoordinates.value,
                                onAddressFetched = { _selectedLocationAddress.value = it })
                        }
                        getEvents()
                    }
                )
            }
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            try {
                val events = eventRepository.getEventsWithFilters(
                    _selectedLocationCoordinates.value.first,
                    _selectedLocationCoordinates.value.second,
                    _distance.value,
                    DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(
                        DateTimeHelper.getCurrentDate(), DateTimeHelper.getCurrentTime()
                    ),
                    null,
                    null
                ).map { it.asDomainModel() }
                _eventsList.emit(events)
            } catch (e: Exception) {
                println("Error loading events: ${e.message}")
            }
        }
    }

    fun onLocationChange(newLocation: String){
        _selectedLocationAddress.value = newLocation
        viewModelScope.launch {
            locationAndGeocodingHelper.getLatLngFromPlace(newLocation, onLatLngFetched = {
                _selectedLocationCoordinates.value = it
                getEvents()
            })
        }
    }

    fun onDistanceChange(newDistance: Int){
        _distance.value = newDistance
        getEvents()
    }

}