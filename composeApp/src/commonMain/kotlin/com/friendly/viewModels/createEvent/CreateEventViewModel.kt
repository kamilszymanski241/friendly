package com.friendly.viewModels.createEvent

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.EventDTO
import com.friendly.dtos.EventUserDTO
import com.friendly.helpers.DateTimeHelper
import com.friendly.helpers.DateTimeHelper.Companion.convertMillisToDate
import com.friendly.helpers.LocationAndGeocodingHelper
import com.friendly.managers.ISessionManager
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
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

    private val eventUserRepository: IEventUserRepository by inject()

    private val storageRepository: IStorageRepository by inject()

    private val sessionManager: ISessionManager by inject()

    private val locationAndGeocodingHelper: LocationAndGeocodingHelper by inject()

    private val _title = MutableStateFlow("")
    val title: Flow<String> = _title

    private val _description = MutableStateFlow("")
    val description: Flow<String> = _description

    private val _startDate = MutableStateFlow<String?>(DateTimeHelper.getCurrentDateAsString())
    val startDate: Flow<String?> = _startDate

    private val _endDate = MutableStateFlow<String?>(DateTimeHelper.getCurrentDateAsString())
    val endDate: Flow<String?> = _endDate

    private val _startTime = MutableStateFlow<String?>(DateTimeHelper.getCurrentTimeAsString())
    val startTime: Flow<String?> = _startTime

    private val _endTime = MutableStateFlow<String?>(DateTimeHelper.getCurrentTimeAsString())
    val endTime: Flow<String?> = _endTime

    private val _maxParticipants = MutableStateFlow("")
    val maxParticipants: Flow<String> = _maxParticipants

    private val _eventPicture = MutableStateFlow<ImageBitmap?>(null)
    val eventPicture: StateFlow<ImageBitmap?> = _eventPicture.asStateFlow()

    private val _selectedLocationCoordinates = MutableStateFlow(Pair(0.0, 0.0))
    val selectedLocationCoordinates: StateFlow<Pair<Double, Double>> = _selectedLocationCoordinates

    private val _selectedLocationAddress = MutableStateFlow("")
    val selectedLocationAddress: StateFlow<String> = _selectedLocationAddress

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

    fun onStartDateChange(date: Long?) {
        if(date != null)
        {
            _startDate.value = convertMillisToDate(date)
        }
        else{
            onErrorMessageChange("Something went wrong while picking the date")
        }
    }

    fun onEndDateChange(date: Long?) {
        if(date != null) {
            _endDate.value = convertMillisToDate(date)
        }
        else{
            onErrorMessageChange("Something went wrong while picking the date")
        }
    }

    fun onStartTimeChange(time: String) {
        _startTime.value = time
    }

    fun onEndTimeChange(time: String) {
        _endTime.value = time
    }

    fun onErrorMessageChange(errorMessage: String) {
        _errorMessage.value = errorMessage
    }

    fun setInitialLocation(){
        viewModelScope.launch {
            locationAndGeocodingHelper.getLastLocation(onLocationRetrieved = {
                _selectedLocationCoordinates.value = it
            }, onPermissionDenied = {})
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

    fun updateCoordinatesOnAddressChange (){
        viewModelScope.launch {
            locationAndGeocodingHelper.getLatLngFromPlace(
                place = _selectedLocationAddress.value,
                onLatLngFetched = { onLocationChange(it) })
        }
    }

    fun onConfirmBasicDetails(onSuccess: () -> Unit, onFailure: () -> Unit) {
        if (_title.value.isEmpty() || _title.value.toCharArray().size < 10){
            onErrorMessageChange("The title must be at least 10 characters")
            onFailure()
        }
        else{
            onSuccess()
        }
    }

    fun onConfirmMoreDetails(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val maxParticipantsInt = _maxParticipants.value.toIntOrNull()
        val startTime = _startTime.value
        val endTime = _endTime.value
        val startDate = _startDate.value
        val endDate = _endDate.value

        if (maxParticipantsInt != null && maxParticipantsInt > 0 && startTime != null && endTime != null && startDate != null && endDate != null) {
            if (startDate == endDate && startTime <= endTime) {
                onSuccess()
                return
            }
            if (startDate < endDate) {
                onSuccess()
                return
            }
        }
        onFailure()
    }


    fun onConfirm(onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                val startDate = _startDate.value
                val startTime = _startTime.value
                val endDate = _endDate.value
                val endTime = _endTime.value
                val currentUserId = sessionManager.currentUser.value?.id
                val locationCoordinates = _selectedLocationCoordinates.value
                val maxParticipantsInt = _maxParticipants.value.toIntOrNull()
                if (startDate != null && startTime != null && endDate != null && endTime != null && currentUserId != null && maxParticipantsInt != null) {
                    val startDateTime = DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(startDate, startTime)
                    val endDateTime = DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(endDate, endTime)
                    val locationCoordinatesString = "POINT(${locationCoordinates.first} ${locationCoordinates.second})"

                    val eventDTO = EventDTO(
                        title = _title.value,
                        description = _description.value,
                        locationWKB = locationCoordinatesString,
                        startDateTime = startDateTime,
                        endDateTime = endDateTime,
                        locationText = _selectedLocationAddress.value,
                        maxParticipants = maxParticipantsInt,
                        organizer = currentUserId
                    )

                    val event = eventsRepository.postEvent(eventDTO)

                    if (event.id != null) {
                        eventUserRepository.addUserToEvent(EventUserDTO(eventId = event.id, userId = currentUserId))

                        val eventPicture = _eventPicture.value
                        if (eventPicture != null) {
                            if (storageRepository.uploadEventPicture(event.id, eventPicture)) {
                                onSuccess()
                            } else {
                                onFailure()
                            }
                        } else {
                            onSuccess()
                        }
                    } else {
                        onFailure()
                    }
                } else {
                    onErrorMessageChange("Invalid input values")
                    onFailure()
                }
            } catch (e: Exception) {
                println("Error while adding new event: ${e.message}")
                onErrorMessageChange("Something went wrong")
                onFailure()
            }
        }
    }

}