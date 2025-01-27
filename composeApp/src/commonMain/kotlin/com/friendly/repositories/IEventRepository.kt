package com.friendly.repositories

import com.friendly.dtos.EventDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface IEventRepository {
    suspend fun getEventsWithFilters(lat:Double, lng: Double, distance: Int, endTime: String, tag: Int?, query: String?): List<EventDTO>
    suspend fun getSingleEventWithParticipants(eventId: String): EventDTO
    suspend fun getMultipleEventsByIDs(eventIds: List<String>): List<EventDTO>
    suspend fun getEventsByOrganizer(userId: String): List<EventDTO>

    suspend fun postEvent(eventDTO: EventDTO): EventDTO


    suspend fun changeTitle(eventId: String, title: String): Boolean

    suspend fun changeDescription(eventId: String, description: String): Boolean

    suspend fun changeDateTimes(eventId: String, startDateTime: String, endDateTime: String): Boolean

    suspend fun changeLocation(eventId: String, coordinates: Pair<Double,Double>, locationText: String): Boolean

    suspend fun changeMaxParticipants(eventId: String, maxParticipants: Int): Boolean
}