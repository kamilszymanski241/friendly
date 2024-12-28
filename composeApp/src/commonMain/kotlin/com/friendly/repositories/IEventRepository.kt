package com.friendly.repositories

import com.friendly.dtos.EventDTO

interface IEventRepository {
    suspend fun getEventsWithParticipants(): List<EventDTO>
    suspend fun getEventWithParticipants(eventId: String): EventDTO
    suspend fun getEventsByUserId(userId: String): List<EventDTO>
}