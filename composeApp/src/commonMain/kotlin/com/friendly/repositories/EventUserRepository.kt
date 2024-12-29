package com.friendly.repositories

import com.friendly.dtos.EventUserDTO
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventUserRepository: IEventUserRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getEventParticipants(eventId: String): List<String> {
        return try {
            postgrest.from("EventsUsers").select {
                filter {
                    eq("eventId", eventId)
                }
            }.decodeList<EventUserDTO>().map { it.userId }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAllUserEvents(userId: String): List<String> {
        return try {
            postgrest.from("EventsUsers").select {
                filter{
                    eq("userId", userId)
                }
            }.decodeList<EventUserDTO>().map { it.eventId }
        }catch(e: Exception)
        {
            throw e
        }
    }

    override suspend fun addUserToEvent(eventUserDTO: EventUserDTO) {
        try{
            postgrest.from("EventsUsers").insert(eventUserDTO)
        }
        catch(e: Exception)
        {
            throw e
        }
    }

    override suspend fun removeUserFromEvent(userId: String, eventId: String){
        try {
            postgrest.from("EventsUsers").delete {
                filter {
                    and{
                        eq("userId",userId)
                        eq("eventId", eventId)
                    }
                }
            }
        }catch(e: Exception)
        {
            throw e
        }
    }
}