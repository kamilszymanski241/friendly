package com.friendly.models

data class UserDetails(

    val id : String,

    val name : String,

    val surname : String,

    val joined: String,

    val description: String,

    val gender: Gender,

    val dateOfBirth: String,

    val age: Int,

    val profilePictureUrl: String,

    val events: List<Event>? = null
)