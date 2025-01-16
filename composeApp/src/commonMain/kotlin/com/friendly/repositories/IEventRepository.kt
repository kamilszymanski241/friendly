package com.friendly.repositories

import com.friendly.dtos.EventDTO

interface IEventRepository {
    suspend fun getEventsWithParticipants(): List<EventDTO>
    suspend fun getSingleEventWithParticipants(eventId: String): EventDTO
    suspend fun getMultipleEventsByIDs(eventIds: List<String>): List<EventDTO>
    suspend fun getEventsByOrganizer(userId: String): List<EventDTO>

    suspend fun postEvent(eventDTO: EventDTO): EventDTO
}