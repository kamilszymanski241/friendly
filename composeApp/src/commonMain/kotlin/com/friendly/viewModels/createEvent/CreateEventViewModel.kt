package com.friendly.viewModels.createEvent

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.EventDTO
import com.friendly.dtos.EventUserDTO
import com.friendly.helpers.DateTimeHelper
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
            onFailure()
        }
        else{
            onSuccess()
        }
    }

    fun onConfirmMoreDetails(onSuccess: () -> Unit, onFailure: () -> Unit) {
        val maxParticipantsInt = _maxParticipants.value.toIntOrNull()
        if (maxParticipantsInt != null && _startTime.value != null && _endTime.value != null && _startDate.value != null && _endDate.value != null){
            if(maxParticipantsInt > 0){
                if(_startDate.value == _endDate.value){
                    if(_startTime.value!! <= _endTime.value!!){
                        onSuccess()
                    }
                }
                else{
                    if(_startDate.value!! < _endDate.value!!){
                        onSuccess()
                    }
                }
            }
        }
        onFailure()
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
                "POINT(${_selectedLocationCoordinates.value?.first} ${_selectedLocationCoordinates.value?.second})"
            println(locationCoordinates)
            val eventDTO = EventDTO(
                title = _title.value,
                description = _description.value,
                locationWKB = locationCoordinates,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                locationText = _selectedLocationAddress.value,
                maxParticipants = _maxParticipants.value.toInt(),
                organizer = sessionManager.currentUser.value!!.id,
            )
            try {
                val event = eventsRepository.postEvent(eventDTO)
                if(event.id != null) {
                    eventUserRepository.addUserToEvent(EventUserDTO(eventId = event.id, userId = sessionManager.currentUser.value!!.id))
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