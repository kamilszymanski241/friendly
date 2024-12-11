package com.friendly.dtos

import com.friendly.models.UserDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsDTO (

    @SerialName("id")
    val id: String,

    @SerialName("created_at")
    val createdAt: String = "",

    @SerialName("name")
    val name: String,

    @SerialName("surname")
    val surname: String

    ) {
    fun asDomainModel(): UserDetails {
        return UserDetails(
            id = this.id,
            joined = this.createdAt,
            name = this.name,
            surname = this.surname
        )
    }
}