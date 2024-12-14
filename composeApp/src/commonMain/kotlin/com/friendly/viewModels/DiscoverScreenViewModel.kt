package com.friendly.viewModels

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.compressBitmapToDesiredSize
import com.friendly.models.Event
import com.friendly.repositories.IEventRepository
import com.friendly.repositories.IEventUserRepository
import com.friendly.repositories.IStorageRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DiscoverScreenViewModel: ViewModel(), KoinComponent {

    private val eventRepository: IEventRepository by inject()

    private val eventUserRepository: IEventUserRepository by inject()

    private val storageRepository: IStorageRepository by inject()

    private val _eventsList = MutableStateFlow<List<Pair<Event, List<ImageBitmap>>>?>(null)
    val eventsList: Flow<List<Pair<Event, List<ImageBitmap>>>?> = _eventsList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

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
                val events = eventRepository.getEvents().map { it.asDomainModel() }
                val eventsWithProfilePictures = events.map { event ->
                    async {
                        val userIds = eventUserRepository.getEventUsers(event.id)
                        val profilePictures = userIds.mapNotNull { userId ->
                            async {
                                try {
                                    storageRepository.fetchProfilePicture(userId)
                                } catch (e: Exception) {
                                    println("Error fetching profile picture for user $userId: ${e.message}")
                                    null
                                }
                            }.await()
                        }
                        event to profilePictures
                    }
                }.awaitAll()
                _eventsList.emit(eventsWithProfilePictures)
            } catch (e: Exception) {
                println("Error loading events: ${e.message}")
            }
        }
    }

}