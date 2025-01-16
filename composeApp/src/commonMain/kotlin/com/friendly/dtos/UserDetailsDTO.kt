package com.friendly.dtos

import Friendly.composeApp.BuildConfig
import com.friendly.models.Event
import com.friendly.models.UserDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsDTO (

    @SerialName("id")
    val id: String,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("name")
    val name: String,

    @SerialName("surname")
    val surname: String,

    @SerialName("events")
    val events: List<EventDTO>? = null

    ) {
    fun asDomainModel(): UserDetails {
        return UserDetails(
            id = this.id,
            joined = this.createdAt ?: "", //TODO()
            name = this.name,
            surname = this.surname,
            profilePictureUrl = BuildConfig.SUPABASE_URL+BuildConfig.PROFILE_PICTURES_STORAGE_URL + "${this.id}.jpg",
            events =  this.events?.map {  it.asDomainModel() }
        )
    }
}