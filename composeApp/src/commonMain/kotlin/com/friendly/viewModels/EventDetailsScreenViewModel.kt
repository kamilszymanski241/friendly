package com.friendly.viewModels

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
    private val eventUserDetailsRepository: IEventUserRepository by inject()
    private val sessionManager: ISessionManager by inject()

    enum class EventDetailsButtonType {
        PleaseSignIn,
        Join,
        Edit,
        Quit
    }

    private val _eventDetails = MutableStateFlow<Event?>(null)
    val eventDetails: Flow<Event?> = _eventDetails

    private val _buttonType = MutableStateFlow(EventDetailsButtonType.PleaseSignIn)
    val buttonType: Flow<EventDetailsButtonType> = _buttonType

    init {
        viewModelScope.launch {
            _eventDetails.value = eventRepository.getSingleEventWithParticipants(eventId).asDomainModel()
            if (sessionManager.sessionStatus.value == SessionStatus.NotAuthenticated(false) ||
                sessionManager.sessionStatus.value == SessionStatus.NotAuthenticated(true)
            ) {
                _buttonType.value = EventDetailsButtonType.PleaseSignIn
            } else {
                if (_eventDetails.value!!.organizer == sessionManager.currentUser.value!!.id) {
                    _buttonType.value = EventDetailsButtonType.Edit
                } else if (eventId in eventUserDetailsRepository.getAllUserEvents(sessionManager.currentUser.value!!.id)) {
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
                eventUserDetailsRepository.addUserToEvent(eventUser)
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
                eventUserDetailsRepository.removeUserFromEvent(
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