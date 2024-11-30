package com.friendly.repositories

import com.friendly.DTOs.EventDTO
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventRepository: IEventRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getEvents(): List<EventDTO> {
        return withContext(Dispatchers.IO){
            val result = postgrest.from("Events")
                .select().decodeList<EventDTO>()
            result
        }
    }
}