package com.friendly.viewModels.eventDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShowAllParticipantsViewModel (private val eventId: String): ViewModel(), KoinComponent {
    val eventRepository: IEventRepository by inject()
    val eventUserRepository: IEventUserRepository by inject()
    private val sessionManager: ISessionManager by inject()

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _isOrganizer = MutableStateFlow(false)
    val isOrganizer: Flow<Boolean> = _isOrganizer

    private val _eventDetails = MutableStateFlow<Event?>(null)
    val eventDetails: Flow<Event?> = _eventDetails

    fun initialize(){
        viewModelScope.launch {
            _eventDetails.value = eventRepository.getSingleEventWithParticipants(eventId).asDomainModel()
            val userId = sessionManager.currentUser.value?.id
            val eventOrganizerID = _eventDetails.value?.organizer
            if(userId != null && eventOrganizerID != null)
            {
                _isOrganizer.value = userId == eventOrganizerID
            }
        }
    }
    fun refresh(){
        _isRefreshing.value = true
        _eventDetails.value = null
        initialize()
        _isRefreshing.value = false
    }
    fun removeParticipant(userId: String){
        viewModelScope.launch {
            eventUserRepository.removeUserFromEvent(userId, eventId)
            refresh()
        }
    }
}