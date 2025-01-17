package com.friendly.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import kotlinx.coroutines.flow.Flow
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

    private val _tags = MutableStateFlow<List<Int>>(emptyList())
    val tags: StateFlow<List<Int>> = _tags

    private val _x = MutableStateFlow<String>("emptyList()")
    val x: StateFlow<String> = _x


    fun initialize(){
        if(_eventsList.value == null)
        {
            viewModelScope.launch {
                getEvents()
            }
        }
        locationAndGeocodingHelper.getLastLocation(onPermissionDenied = {}, onLocationRetrieved = {location-> _x.value="${location.first} ${location.second}"})
    }

    private fun getEvents() {
        viewModelScope.launch {
            try {
                val events = eventRepository.getEventsWithFilters().map { it.asDomainModel() }
                _eventsList.emit(events)

            } catch (e: Exception) {
                println("Error loading events: ${e.message}")
            }
        }
    }

    fun getTagsString(): String{
        return if(_tags.value.isNotEmpty()) {
            _tags.value.joinToString { ", " }
        } else{
            "-"
        }
    }
}