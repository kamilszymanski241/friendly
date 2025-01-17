package com.friendly.viewModels.createEvent

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.EventDTO
import com.friendly.helpers.DateTimeHelper
import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.managers.ISessionManager
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateEventViewModel (): ViewModel(), KoinComponent {

    private val eventsRepository: IEventRepository by inject()

    private val storageRepository: IStorageRepository by inject()

    private val sessionManager: ISessionManager by inject()

    private val locationAndGeocodingHelper: LocationAndGeocodingHelper by inject()

    private val _title = MutableStateFlow("")
    val title: Flow<String> = _title

    private val _description = MutableStateFlow("")
    val description: Flow<String> = _description

    private val _startDate = MutableStateFlow<String?>(DateTimeHelper.getCurrentDate())
    val startDate: Flow<String?> = _startDate

    private val _endDate = MutableStateFlow<String?>(DateTimeHelper.getCurrentDate())
    val endDate: Flow<String?> = _endDate

    private val _startTime = MutableStateFlow<String?>(DateTimeHelper.getCurrentTime())
    val startTime: Flow<String?> = _startTime

    private val _endTime = MutableStateFlow<String?>(DateTimeHelper.getCurrentTime())
    val endTime: Flow<String?> = _endTime

    private val _maxParticipants = MutableStateFlow("")
    val maxParticipants: Flow<String> = _maxParticipants

    private val _eventPicture = MutableStateFlow<ImageBitmap?>(null)
    val eventPicture: StateFlow<ImageBitmap?> = _eventPicture.asStateFlow()

    private val _selectedLocation = MutableStateFlow(Pair(0.0, 0.0))
    val selectedLocation: StateFlow<Pair<Double, Double>> = _selectedLocation

    private val _selectedLocationText = MutableStateFlow("")
    val selectedLocationText: StateFlow<String> = _selectedLocationText

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: Flow<String?> = _errorMessage

    fun onTitleChange(name: String) {
        _title.value = name
    }

    fun onDescriptionChange(surname: String) {
        _description.value = surname
    }

    fun onPictureChange(picture: ImageBitmap?) {
        _eventPicture.value = picture
    }

    fun onMaxParticipantsChange(newMax: String) {
        _maxParticipants.value = newMax
    }

    fun onStartDateChange(date: String) {
        _startDate.value = date
    }

    fun onEndDateChange(date: String) {
        _endDate.value = date
    }

    fun onStartTimeChange(time: String) {
        _startTime.value = time
    }

    fun onEndTimeChange(time: String) {
        _endTime.value = time
    }

    fun setInitialLocation(){
        locationAndGeocodingHelper.getLastLocation(onLocationRetrieved = {_selectedLocation.value = it}, onPermissionDenied = {})//TODO()
        locationAndGeocodingHelper.fetchAddress(latLng = _selectedLocation.value, onAddressFetched = {_selectedLocationText.value = it})
    }

    fun onLocationChange(location: Pair<Double, Double>) {
        _selectedLocation.value = location
    }

    fun onLocationTextChange(locationText: String) {
        _selectedLocationText.value = locationText
        locationAndGeocodingHelper.getLatLngFromPlace(locationText, onLatLngFetched = {onLocationChange(it)})
    }

    fun onConfirm(onSuccess: ()->Unit, onFailure: ()->Unit) {
        viewModelScope.launch {
            val startDateTime = DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(
                _startDate.value!!,
                _startTime.value!!
            )
            val endDateTime = DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(
                _endDate.value!!,
                _endTime.value!!
            )
            val locationCoordinates =
                "POINT(${_selectedLocation.value?.first} ${_selectedLocation.value?.second})"
            println(locationCoordinates)
            val eventDTO = EventDTO(
                title = _title.value,
                description = _description.value,
                locationWKB = locationCoordinates,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                locationText = _selectedLocationText.value,
                maxParticipants = _maxParticipants.value.toInt(),
                organizer = sessionManager.currentUser.value!!.id,
            )
            try {
                val event = eventsRepository.postEvent(eventDTO)
                if(event.id != null) {
                    println(event.id)
                    if (_eventPicture.value != null) {
                        if(storageRepository.uploadEventPicture(event.id, _eventPicture.value!!)) {
                            onSuccess()
                        }
                    }
                    else{
                        onSuccess()
                    }
                }
                else{
                    onFailure()
                }
            }catch(e: Exception)
            {
                println("Error while adding new event:" + e.message)
                onFailure()
            }
        }
    }
}