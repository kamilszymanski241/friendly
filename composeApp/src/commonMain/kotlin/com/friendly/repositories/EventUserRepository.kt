package com.friendly.repositories

import com.friendly.dtos.EventUserDTO
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventUserRepository: IEventUserRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getEventUsers(id: String): List<String> {
        return try {
            postgrest.from("EventsUsers").select(){
                filter {
                    eq("eventId", id)
                }}.decodeList<EventUserDTO>().map { it.userId }
        }catch(e: Exception){
            throw e
        }
    }
    override suspend fun addUserToEvent(eventUserDTO: EventUserDTO){
        postgrest.from("EventsUsers").insert(eventUserDTO)
    }
}