package com.friendly.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(

    @SerialName("eventId")
    val eventId : Int,

    @SerialName("dateTime")
    val dateTime : String,

    @SerialName("address")
    val address : String,

    @SerialName("title")
    val title : String,

    @SerialName("description")
    val description : String,

    @SerialName("maxParticipants")
    val maxParticipants: Int,

    @SerialName("users")
    var users : List<User> = emptyList()
)
