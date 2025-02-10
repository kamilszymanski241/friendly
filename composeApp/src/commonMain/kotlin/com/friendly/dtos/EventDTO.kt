package com.friendly.dtos

import Friendly.composeApp.BuildConfig
import com.friendly.helpers.DateTimeHelper
import com.friendly.models.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventDTO (

    @SerialName("id")
    val id: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("title")
    val title: String,

    @SerialName("location_WKB")
    val locationWKB: String? = null,

    @SerialName("location_lat")
    val locationLatitude: Double? = null,

    @SerialName("location_long")
    val locationLongitude: Double? = null,

    @SerialName("location_text")
    val locationText: String,

    @SerialName("description")
    val description: String?,

    @SerialName("max_participants")
    val maxParticipants: Int,

    @SerialName("start_date_time")
    val startDateTime: String,

    @SerialName("end_date_time")
    val endDateTime: String,

    @SerialName("organizer")
    val organizer: String,

    @SerialName("UserDetails")
    val participants: List<UserDetailsDTO>? = null

) {
    fun asDomainModel(): Event {
        return Event(

            id = this.id ?: "",

            createdAt = this.createdAt ?: "",

            title = this.title,

            locationCoordinates = Pair(this.locationLatitude ?: 0.0, this.locationLongitude ?: 0.0),

            locationText = this.locationText,

            description = this.description,

            maxParticipants = this.maxParticipants,

            startDate = DateTimeHelper.parseDateFromSupabaseTimestampzToString(this.startDateTime),

            startTime = DateTimeHelper.parseTimeFromSupabaseTimestampz(this.startDateTime),

            endDate = DateTimeHelper.parseDateFromSupabaseTimestampzToString(this.endDateTime),

            endTime = DateTimeHelper.parseTimeFromSupabaseTimestampz(this.endDateTime),

            organizer = this.organizer,

            eventPictureUrl = BuildConfig.SUPABASE_URL + BuildConfig.EVENT_PICTURES_STORAGE_URL + "${this.id}/event.jpg",

            participants = this.participants?.map { it.asDomainModel() }
        )
    }
}