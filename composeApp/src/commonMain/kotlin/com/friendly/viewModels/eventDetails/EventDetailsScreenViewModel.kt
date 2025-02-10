package com.friendly.viewModels.eventDetails

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.EventUserDTO
import com.friendly.helpers.CacheHelper
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import com.friendly.managers.ISessionManager
import com.friendly.repositories.IStorageRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventDetailsScreenViewModel(private val eventId: String): ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()
    private val storageRepository: IStorageRepository by inject()
    private val eventUserRepository: IEventUserRepository by inject()
    private val sessionManager: ISessionManager by inject()
    private val cacheHelper: CacheHelper by inject()

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    enum class EventDetailsButtonType {
        PleaseSignIn,
        Join,
        None,
        Quit
    }
    private val _isViewedByOrganizer = MutableStateFlow(false)
    val isViewedByOrganizer: Flow<Boolean> = _isViewedByOrganizer

    private val _eventDetails = MutableStateFlow<Event?>(null)
    val eventDetails: Flow<Event?> = _eventDetails

    private val _buttonType = MutableStateFlow(EventDetailsButtonType.PleaseSignIn)
    val buttonType: Flow<EventDetailsButtonType> = _buttonType

    private val _isEventDeleted = MutableStateFlow(false)
    val isEventDeleted: Flow<Boolean> = _isEventDeleted

    private val _isNotFull = MutableStateFlow(true)
    val isNotFull: Flow<Boolean> = _isNotFull

    fun refresh(){
        _isRefreshing.value = true
        _eventDetails.value = null
        initialize()
        _isRefreshing.value = false
    }

    fun initialize() {
        viewModelScope.launch {
            _eventDetails.value =
                eventRepository.getSingleEventWithParticipants(eventId).asDomainModel()
            val pictureURL = _eventDetails.value?.eventPictureUrl
            if(pictureURL != null)
            {
                cacheHelper.clearFromCacheByKey(pictureURL)
            }
            _eventDetails.value?.let { eventDetails ->
                val maxParticipants = eventDetails.maxParticipants
                val currentParticipants = eventDetails.participants?.size ?: 0

                _isNotFull.value = maxParticipants > currentParticipants
            }
            val sessionStatus = sessionManager.sessionStatus.value
            val currentUserId = sessionManager.currentUser.value?.id
            val eventDetails = _eventDetails.value

            if (sessionStatus == SessionStatus.NotAuthenticated(false) || sessionStatus == SessionStatus.NotAuthenticated(true)) {
                _buttonType.value = EventDetailsButtonType.PleaseSignIn
            } else {
                if (eventDetails?.organizer == currentUserId) {
                    _buttonType.value = EventDetailsButtonType.None
                    _isViewedByOrganizer.value = true
                } else if (currentUserId != null && eventId in eventUserRepository.getAllUserEvents(currentUserId)) {
                    _buttonType.value = EventDetailsButtonType.Quit
                } else {
                    _buttonType.value = EventDetailsButtonType.Join
                }
            }

        }
    }

    fun onJoin() {
        viewModelScope.launch {
            try {
                val userId=sessionManager.currentUser.value?.id
                if(userId != null)
                {
                    val eventUser =
                    EventUserDTO(
                        eventId,
                        userId
                    )
                eventUserRepository.addUserToEvent(eventUser)
                }
                else{
                    throw Exception("No user id")
                }
            }
            catch (e: Exception)
            {
                println(e.message)
            }
        }
    }

    fun onQuit(){
        viewModelScope.launch {
            val userId = sessionManager.currentUser.value?.id
            try {
                if(userId != null)
                {
                    eventUserRepository.removeUserFromEvent(
                        userId,
                        eventId
                    )
                }
                else{
                    throw Exception("No user id")
                }
            }
            catch (e: Exception)
            {
                println(e.message)
            }
        }
    }
    fun changeEventPicture(picture: ImageBitmap) {
        val pictureURL = _eventDetails.value?.eventPictureUrl
        _eventDetails.value = null
        viewModelScope.launch {
            try {
                if (storageRepository.upsertEventPicture(
                        eventId,
                        picture
                    )
                ) {
                    if(pictureURL != null)
                    {
                        cacheHelper.clearFromCacheByKey(pictureURL)
                    }
                    initialize()
                }
            } catch (e: Exception) {
                println("Couldn't update photo: ${e.message}")
                initialize()
            }
        }
    }
    fun removeParticipant(userId: String){
        viewModelScope.launch {
            eventUserRepository.removeUserFromEvent(userId, eventId)
            refresh()
        }
    }
    fun onEventDelete(eventId: String){
        _eventDetails.value = null
        viewModelScope.launch {
            eventRepository.deleteEvent(eventId)
            storageRepository.deleteEventPicture(eventId)
            _isEventDeleted.value = true
        }
    }
}