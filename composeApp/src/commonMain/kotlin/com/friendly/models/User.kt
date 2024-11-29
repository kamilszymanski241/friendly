package com.friendly.models

import com.friendly.models.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(

    @SerialName("userId")
    val userId : Int,

    @SerialName("name")
    val name : String,

    @SerialName("surname")
    val surname : String,

    @SerialName("age")
    val age : Int,

    @SerialName("events")
    var events : List<Event> = emptyList()
)
