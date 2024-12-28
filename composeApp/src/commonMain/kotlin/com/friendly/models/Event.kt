package com.friendly.models

data class Event(

    val id: String,

    val createdAt: String,

    val title: String,

    val country: String,

    val city: String,

    val postalCode: String,

    val address: String,

    val description: String?,

    val maxParticipants: Int?,

    val date: String,

    val time: String,

    val participants: List<UserDetails>? = null

)
