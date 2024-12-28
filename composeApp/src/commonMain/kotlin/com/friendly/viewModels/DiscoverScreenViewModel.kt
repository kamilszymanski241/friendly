package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import com.friendly.repositories.IStorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiscoverScreenViewModel: ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()

    private val eventUserRepository: IEventUserRepository by inject()

    private val storageRepository: IStorageRepository by inject()

    private val _eventsList = MutableStateFlow<List<Event>?>(null)
    val eventsList: Flow<List<Event>?> = _eventsList


    init{
        if(_eventsList.value == null)
        {
            viewModelScope.launch {
                getEvents()
            }
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            try {
                val events = eventRepository.getEventsWithParticipants().map { it.asDomainModel() }
                _eventsList.emit(events)

            } catch (e: Exception) {
                println("Error loading events: ${e.message}")
            }
        }
    }

}