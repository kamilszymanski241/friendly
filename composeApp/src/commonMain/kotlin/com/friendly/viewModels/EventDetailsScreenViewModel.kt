package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.EventUserDTO
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import com.friendly.session.ISessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventDetailsScreenViewModel(private val eventId: String): ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()
    private val eventUserDetailsRepository: IEventUserRepository by inject()
    private val sessionManager: ISessionManager by inject()

    private val _eventDetails = MutableStateFlow<Event?>(null)
    val eventDetails: Flow<Event?> = _eventDetails

    suspend fun loadEvent() {
        viewModelScope.launch {
            _eventDetails.value = eventRepository.getEvent(eventId).asDomainModel()
        }
    }
        fun onJoin() {
            viewModelScope.launch {
                if (sessionManager.currentUser.value != null) {
                    val eventUser =
                        EventUserDTO(
                            _eventDetails.value!!.id,
                            sessionManager.currentUser.value!!.id
                        )
                    eventUserDetailsRepository.addUserToEvent(eventUser)
                }
            }
        }
}