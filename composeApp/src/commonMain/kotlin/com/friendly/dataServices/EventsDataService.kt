package com.friendly.dataServices

import com.friendly.httpClient
import com.friendly.models.Event
import com.friendly.network.apiUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EventsDataService() : IEventsDataService, KoinComponent {
    private val client = httpClient()

    override suspend fun getEventsWithUsers(): List<Event> {
        try {
            val events: List<Event> = client.get("$apiUrl/events").body()
            return events
        } catch (e: Exception) {
            println(e.message)
        }
        return emptyList()
    }

    override suspend fun createNewEvent(event: Event) {
        val response: HttpResponse = client.post("$apiUrl/events") {
            contentType(ContentType.Application.Json)
            setBody(event)
        }
    }
}