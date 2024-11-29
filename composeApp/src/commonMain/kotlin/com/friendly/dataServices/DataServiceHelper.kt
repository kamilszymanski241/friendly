package com.friendly.dataServices

import com.friendly.models.Event
import com.friendly.Platform
import com.friendly.getPlatform
import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataServiceHelper: KoinComponent {

    private val eventDataService: IEventsDataService by inject()

    suspend fun getEventsFromAPI() :List<Event>{
        val response=eventDataService.getEventsWithUsers()
        return response
    }

    suspend fun createNewEvent(event: Event){
        eventDataService.createNewEvent(event)
    }
}