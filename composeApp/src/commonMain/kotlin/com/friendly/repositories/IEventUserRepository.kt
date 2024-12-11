package com.friendly.repositories

import com.friendly.dtos.EventUserDTO

interface IEventUserRepository {
    suspend fun getEventUsers(id: String): List<String>
    suspend fun addUserToEvent(eventUserDTO: EventUserDTO)
}