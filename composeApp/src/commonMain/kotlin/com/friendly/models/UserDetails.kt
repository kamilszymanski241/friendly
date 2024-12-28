package com.friendly.models

data class UserDetails(

    val id : String,

    val name : String,

    val surname : String,

    val joined: String,

    val profilePictureUrl: String,

    val events: List<Event>? = null
)
