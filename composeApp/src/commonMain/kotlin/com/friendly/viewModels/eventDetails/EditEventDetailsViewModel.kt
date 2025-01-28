package com.friendly.viewModels.eventDetails
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.helpers.DateTimeHelper
import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.managers.ISessionManager
import com.friendly.models.Event
import com.friendly.models.Gender
import com.friendly.models.UserDetails
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IUserDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditEventDetailsViewModel(private val eventId: String): ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()
    private val locationAndGeocodingHelper: LocationAndGeocodingHelper by inject()

    private var _event = MutableStateFlow<Event?>(null)
    val event: StateFlow<Event?> = _event

    private var _updated = MutableStateFlow(false)
    val updated: StateFlow<Boolean> = _updated

    private val _selectedLocationCoordinates = MutableStateFlow(Pair(0.0, 0.0))
    val selectedLocationCoordinates: StateFlow<Pair<Double, Double>> = _selectedLocationCoordinates

    private val _selectedLocationAddress = MutableStateFlow("")

    init {
        fetchEventDetails()
        if (event.value != null) {
            onLocationTextChange(event.value!!.locationText)
        }
    }

    fun fetchEventDetails() {
        viewModelScope.launch {
            _event.value =
                eventRepository.getSingleEventWithParticipants(eventId).asDomainModel()
            _updated.value = false
        }
    }

    fun onLocationChange(location: Pair<Double, Double>) {
        _selectedLocationCoordinates.value = location
    }

    fun onLocationTextChange(locationText: String) {
        _selectedLocationAddress.value = locationText
        updateCoordinatesOnAddressChange()
    }

    fun updateAddressOnCoordinatesChange ()
    {
        viewModelScope.launch {
            locationAndGeocodingHelper.fetchAddress(
                latLng = _selectedLocationCoordinates.value,
                onAddressFetched = { _selectedLocationAddress.value = it })
        }
    }

    fun updateCoordinatesOnAddressChange() {
        viewModelScope.launch {
            locationAndGeocodingHelper.getLatLngFromPlace(
                place = _selectedLocationAddress.value,
                onLatLngFetched = { onLocationChange(it) })
        }
    }

    fun changeTitle(title: String) {
        _event.value = null
        viewModelScope.launch {
            try {
                eventRepository.changeTitle(eventId, title)
                _updated.value = true
            } catch (e: Exception) {
                println("Couldn't change name: ${e.message}")
            }
        }
    }
    fun changeDescription(description: String) {
        _event.value = null
        viewModelScope.launch {
            try {
                eventRepository.changeDescription(eventId, description)
                _updated.value = true
            } catch (e: Exception) {
                println("Couldn't change name: ${e.message}")
            }
        }
    }
    fun changeDateTimes(startDate: String, startTime: String, endDate: String, endTime: String) {
        _event.value = null
        val start = DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(startDate, startTime)
        val end = DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(endDate, endTime)
        viewModelScope.launch {
            try {
                eventRepository.changeDateTimes(eventId, start, end)
                _updated.value = true
            } catch (e: Exception) {
                println("Couldn't change name: ${e.message}")
            }
        }
    }
    fun changeLocation() {
        _event.value = null
        viewModelScope.launch {
            try {
                eventRepository.changeLocation(eventId, _selectedLocationCoordinates.value, _selectedLocationAddress.value)
                _updated.value = true
            } catch (e: Exception) {
                println("Couldn't change name: ${e.message}")
            }
        }
    }
    fun changeMaxParticipants(maxParticipants: Int) {
        _event.value = null
        viewModelScope.launch {
            try {
                eventRepository.changeMaxParticipants(eventId, maxParticipants)
                _updated.value = true
            } catch (e: Exception) {
                println("Couldn't change name: ${e.message}")
            }
        }
    }
}