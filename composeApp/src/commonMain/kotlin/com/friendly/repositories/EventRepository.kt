package com.friendly.repositories

import com.friendly.dtos.EventDTO
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.FilterOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepository: IEventRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getEventsWithParticipants(): List<EventDTO> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, country, city, postal_code, address, description, max_participants, date, time, organizer, UserDetails(id, created_at, name, surname)")
                ) {
                }.decodeList<EventDTO>()
            result
        }
    }

    override suspend fun getEventWithParticipants(eventId: String): EventDTO {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, country, city, postal_code, address, description, max_participants, date, time, organizer, UserDetails(id, created_at, name, surname)")
                ) {
                    filter {
                        eq("id", eventId)
                    }
                }.decodeSingle<EventDTO>()
            result
        }
    }

    override suspend fun getMultipleEvents(eventIds: List<String>): List<EventDTO> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, country, city, postal_code, address, description, max_participants, date, time, organizer, UserDetails(id, created_at, name, surname)")
                ) {
                    filter {
                        isIn("id", eventIds)
                    }
                }.decodeList<EventDTO>()
            result
        }
    }

    override suspend fun getEventsByOrganizer(userId: String): List<EventDTO>{
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, country, city, postal_code, address, description, max_participants, date, time, organizer, UserDetails(id, created_at, name, surname)")
                ) {
                    filter {
                        eq("organizer", userId)
                    }
                }.decodeList<EventDTO>()
            result
        }
    }

}