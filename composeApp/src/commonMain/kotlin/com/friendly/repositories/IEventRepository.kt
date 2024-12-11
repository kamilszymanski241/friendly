package com.friendly.repositories

import com.friendly.dtos.EventDTO

interface IEventRepository {
    suspend fun getEvents(): List<EventDTO>
    suspend fun getEvent(eventId: String): EventDTO
}