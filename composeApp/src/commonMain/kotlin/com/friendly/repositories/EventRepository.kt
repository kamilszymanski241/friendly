package com.friendly.repositories

import com.friendly.dtos.EventDTO
import com.friendly.helpers.DateTimeHelper
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
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
                    Columns.raw("id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails(id, created_at, name, surname)")
                ) {

                }.decodeList<EventDTO>()
            result
        }
    }

    override suspend fun getSingleEventWithParticipants(eventId: String): EventDTO {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails(id, created_at, name, surname)")
                ) {
                    filter {
                        eq("id", eventId)
                    }
                }.decodeSingle<EventDTO>()
            result
        }
    }

    override suspend fun getMultipleEventsByIDs(eventIds: List<String>): List<EventDTO> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw(
                        "id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails( id, created_at, name, surname)"
                    )
                ) {
                    filter {
                        and {
                            isIn("id", eventIds)
                            gte("start_date_time", DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(DateTimeHelper.getCurrentDate(), DateTimeHelper.getCurrentTime()))
                        }
                    }
                    order(column = "start_date_time", order = Order.ASCENDING)
                }.decodeList<EventDTO>()
            result
        }
    }

    override suspend fun getEventsByOrganizer(userId: String): List<EventDTO>{
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails(id, created_at, name, surname)")
                ) {
                    filter {
                        eq("organizer", userId)
                    }
                }.decodeList<EventDTO>()
            result
        }
    }

    override suspend fun postEvent(eventDTO: EventDTO): EventDTO{
        try {
            val eventId = withContext(Dispatchers.IO) {
                postgrest.from("Events").insert(eventDTO) {
                    select()
                }.decodeSingle<EventDTO>()
            }
            return eventId
        } catch (e: Exception) {
            throw e
        }
    }
}