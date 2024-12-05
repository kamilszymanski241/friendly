package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiscoverScreenViewModel: ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()

    private val _eventsList = MutableStateFlow<List<Event>>(listOf())
    val eventsList: Flow<List<Event>> = _eventsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    init{
        viewModelScope.launch {
            getEvents()
        }
    }

    fun getEvents(){
        viewModelScope.launch{
            val events = eventRepository.getEvents()
            _eventsList.emit(events.map { it -> it.asDomainModel() })
        }
    }
}