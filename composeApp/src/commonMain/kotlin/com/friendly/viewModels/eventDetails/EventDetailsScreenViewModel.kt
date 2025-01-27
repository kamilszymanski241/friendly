package com.friendly.viewModels.eventDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.EventUserDTO
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import com.friendly.managers.ISessionManager
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventDetailsScreenViewModel(private val eventId: String): ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()
    private val eventUserRepository: IEventUserRepository by inject()
    private val sessionManager: ISessionManager by inject()

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

    private val _isNotFull = MutableStateFlow(true)
    val isNotFull: Flow<Boolean> = _isNotFull

    init {
        viewModelScope.launch {
            _eventDetails.value =
                eventRepository.getSingleEventWithParticipants(eventId).asDomainModel()
            if (_eventDetails.value!!.maxParticipants <= _eventDetails.value!!.participants!!.size) {
                _isNotFull.value = false
            }
            if (sessionManager.sessionStatus.value == SessionStatus.NotAuthenticated(false) ||
                sessionManager.sessionStatus.value == SessionStatus.NotAuthenticated(true)
            ) {
                _buttonType.value = EventDetailsButtonType.PleaseSignIn
            } else {
                if (_eventDetails.value!!.organizer == sessionManager.currentUser.value!!.id) {
                    _buttonType.value = EventDetailsButtonType.None
                    _isViewedByOrganizer.value = true
                } else if (eventId in eventUserRepository.getAllUserEvents(sessionManager.currentUser.value!!.id)) {
                    _buttonType.value = EventDetailsButtonType.Quit
                } else {
                    _buttonType.value = EventDetailsButtonType.Join
                }
            }
        }
    }

    fun loadEvent() {
        viewModelScope.launch {
            _eventDetails.value = eventRepository.getSingleEventWithParticipants(eventId).asDomainModel()
        }
    }

    fun onJoin() {
        viewModelScope.launch {
            try {
                val eventUser =
                    EventUserDTO(
                        _eventDetails.value!!.id,
                        sessionManager.currentUser.value!!.id
                    )
                eventUserRepository.addUserToEvent(eventUser)
            }
            catch (e: Exception)
            {
                println(e.message)
            }
        }
    }

    fun onEdit(){

    }

    fun onQuit(){
        viewModelScope.launch {
            try {
                eventUserRepository.removeUserFromEvent(
                    sessionManager.currentUser.value!!.id,
                    eventId
                )
            }
            catch (e: Exception)
            {
                println(e.message)
            }
        }
    }
}