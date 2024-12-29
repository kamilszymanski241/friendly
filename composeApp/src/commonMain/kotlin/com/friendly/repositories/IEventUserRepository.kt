package com.friendly.repositories

import com.friendly.dtos.EventUserDTO

interface IEventUserRepository {
    suspend fun getEventParticipants(eventId: String): List<String>
    suspend fun getAllUserEvents(userId: String): List<String>
    suspend fun addUserToEvent(eventUserDTO: EventUserDTO)
    suspend fun removeUserFromEvent(userId: String, eventId: String)
}