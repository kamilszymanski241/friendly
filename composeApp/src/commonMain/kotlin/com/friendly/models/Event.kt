package com.friendly.models

data class Event(

    val id: String,

    val createdAt: String,

    val title: String,

    val locationCoordinates: Pair<Double,Double>,

    val locationText: String,

    val description: String?,

    val maxParticipants: Int,

    val startDate: String,

    val startTime: String,

    val endDate: String,

    val endTime: String,

    val eventPictureUrl: String,

    val organizer: String,

    val participants: List<UserDetails>? = null

)
