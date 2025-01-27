package com.friendly.repositories

import com.friendly.dtos.EventDTO
import com.friendly.helpers.DateTimeHelper
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepository: IEventRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getEventsWithFilters(lat:Double, lng: Double, distance: Int, endTime: String, tag: Int?, query: String?): List<EventDTO> {
        return withContext(Dispatchers.IO) {
            val result = postgrest.rpc(
                function = "get_events_filtered",
                parameters = buildJsonObject {
                    put("lat", lat)
                    put("lng", lng)
                    put("distance", distance*1000)
                    put("endTime", endTime)
                    put("tag", tag)
                    put("query", query)
                }
            )
            println(result.data)
            result.decodeList<EventDTO>()
        }
    }

    override suspend fun getSingleEventWithParticipants(eventId: String): EventDTO {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("Events")
                .select(
                    Columns.raw("id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails(id, created_at, name, surname, date_of_birth, description, gender)")
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
                        "id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails( id, created_at, name, surname, date_of_birth, description, gender)"
                    )
                ) {
                    filter {
                        and {
                            isIn("id", eventIds)
                            gte("start_date_time", DateTimeHelper.convertDateAndTimeToSupabaseTimestamptz(DateTimeHelper.getCurrentDateAsString(), DateTimeHelper.getCurrentTimeAsString()))
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
                    Columns.raw("id, created_at, title, description, location_lat, location_long, location_text, max_participants, start_date_time, end_date_time, organizer, UserDetails(id, created_at, name, surname, date_of_birth, description, gender)")
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

    override suspend fun changeTitle(eventId: String, title: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("Events").update(
                    {
                        set("title", title)
                    }
                ){
                    filter {
                        eq("id", eventId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeDescription(eventId: String, description: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("Events").update(
                    {
                        set("description", description)
                    }
                ){
                    filter {
                        eq("id", eventId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeDateTimes(eventId: String, startDateTime: String, endDateTime: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("Events").update(
                    {
                        set("start_date_time", startDateTime)
                        set("end_date_time", endDateTime)
                    }
                ){
                    filter {
                        eq("id", eventId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeLocation(eventId: String, coordinates: Pair<Double,Double>, locationText: String): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("Events").update(
                    {
                        set("location_WKB", "POINT(${coordinates.first} ${coordinates.second})")
                        set("location_text", locationText)
                    }
                ){
                    filter {
                        eq("id", eventId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }

    override suspend fun changeMaxParticipants(eventId: String, maxParticipants: Int): Boolean{
        return try{
            withContext(Dispatchers.IO){
                postgrest.from("Events").update(
                    {
                        set("max_participants", maxParticipants)
                    }
                ){
                    filter {
                        eq("id", eventId)
                    }
                }
            }
            true
        }catch(e: Exception){
            throw e
        }
    }
}