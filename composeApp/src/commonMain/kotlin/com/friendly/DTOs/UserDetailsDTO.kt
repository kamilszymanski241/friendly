package com.friendly.DTOs

import com.friendly.models.UserDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailsDTO (
    @SerialName("id")
    val userDetailsId: String,

    @SerialName("userId")
    val userId: String,

    @SerialName("created_at")
    val createdAt: String,

    @SerialName("name")
    val name: String,

    @SerialName("surname")
    val surname: String

    ) {
    fun asDomainModel(): UserDetails {
        return UserDetails(
            userDetailsId = this.userDetailsId,
            userId = this.userId,
            joined = this.createdAt,
            name = this.name,
            surname = this.surname
        )
    }
}