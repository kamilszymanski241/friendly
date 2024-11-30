package com.friendly.repositories

import com.friendly.DTOs.EventDTO

interface IEventRepository {
    suspend fun getEvents(): List<EventDTO>
}