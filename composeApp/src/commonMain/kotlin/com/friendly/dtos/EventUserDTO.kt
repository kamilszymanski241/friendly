package com.friendly.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventUserDTO (

    @SerialName("eventId")
    val eventId: String,

    @SerialName("userId")
    val userId: String

)