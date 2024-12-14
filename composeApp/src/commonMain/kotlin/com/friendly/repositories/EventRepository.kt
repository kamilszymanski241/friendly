package com.friendly.repositories

import com.friendly.dtos.EventDTO
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepository: IEventRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getEvents(): List<EventDTO> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select() {
                }.decodeList<EventDTO>()
            result
        }
    }

    override suspend fun getEvent(eventId: String): EventDTO {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select() {
                    filter {
                        eq("id", eventId)
                    }
                }.decodeSingle<EventDTO>()
            result
        }
    }

/*    override suspend fun getUpcomingEvents(userId: String): EventDTO {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select() {
                    filter {
                        eq("id", id)
                    }
                }.decodeSingle<EventDTO>()
            result
        }
    }*/

/*    suspend fun getEventsWithParticipants(eventId: Int): List<EventDTO> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("")
                ) {
                }.decodeList<EventDTO>()
            result
        }
    }*/
}