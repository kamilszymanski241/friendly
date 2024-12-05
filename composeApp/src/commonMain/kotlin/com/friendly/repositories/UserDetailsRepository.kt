package com.friendly.repositories

import com.friendly.DTOs.EventDTO
import com.friendly.DTOs.UserDetailsDTO
import com.friendly.models.UserDetails
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserDetailsRepository: IUserDetailsRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getUserDetails(userId: String): UserDetailsDTO {
        return withContext(Dispatchers.IO) {
            val result = postgrest.from("UserDetails")
                .select() {
                    filter {
                        eq("userId", userId)
                    }
                }.decodeSingle<UserDetailsDTO>()
            result
        }
    }

    override suspend fun createUserDetails(userId: String, name: String, surname: String) {
        TODO("Not yet implemented")
    }
}