package com.friendly.repositories

import com.friendly.DTOs.EventDTO
import com.friendly.models.Event

interface IEventRepository {
    suspend fun getEvents(): List<EventDTO>
}