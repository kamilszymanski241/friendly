package com.friendly.DTOs

import com.friendly.models.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDTO (

    @SerialName("id")
    val eventId: Int,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("title")
    val title: String,

    @SerialName("country")
    val country: String,

    @SerialName("city")
    val city: String,

    @SerialName("postal_code")
    val postalCode: String,

    @SerialName("address")
    val address: String,

    @SerialName("description")
    val description: String?,

    @SerialName("max_participants")
    val maxParticipants: Int?,

    @SerialName("date")
    val date: String,

    @SerialName("time")
    val time: String

) {
    fun asDomainModel(): Event {
        return Event(

            eventId = this.eventId,

            createdAt = this.createdAt,

            title = this.title,

            country = this.country,

            city = this.city,

            postalCode = this.postalCode,

            address = this.address,

            description = this.description,

            maxParticipants = this.maxParticipants,

            date = this.date,

            time = this.time

        )
    }
}