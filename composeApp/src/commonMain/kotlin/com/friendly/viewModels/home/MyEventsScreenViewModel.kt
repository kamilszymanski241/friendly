package com.friendly.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MyEventsScreenViewModel: ViewModel(), KoinComponent {
    private val sessionManager: ISessionManager by inject()
    private val eventRepository: IEventRepository by inject()

    val user: StateFlow<UserInfo?> = sessionManager.currentUser
    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus

    private val _showSignInReminder = MutableStateFlow(false)
    val showSignInReminder: Flow<Boolean> = _showSignInReminder

    private val _eventsList = MutableStateFlow<List<Event>?>(null)
    val eventsList: Flow<List<Event>?> = _eventsList

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    fun refresh(){
        _isRefreshing.value = true
        _eventsList.value = null
        initialize()
        _isRefreshing.value = false
    }

    fun initialize(){
        if(sessionStatus.value == SessionStatus.NotAuthenticated(false) || sessionStatus.value == SessionStatus.NotAuthenticated(true))
        {
            _showSignInReminder.value = true
        }
        else{
            getEvents()
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            try {
                val events = eventRepository.getEventsByOrganizer(user.value!!.id).map { it.asDomainModel() }
                _eventsList.value = events

            } catch (e: Exception) {
                println("Error loading events: ${e.message}")
            }
        }
    }
}