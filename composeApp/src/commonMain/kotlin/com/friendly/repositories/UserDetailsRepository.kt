package com.friendly.repositories

import com.friendly.DTOs.UserDetailsDTO
import com.friendly.models.UserDetails
import com.friendly.session.ISessionManager
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.network.SupabaseApi
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserDetailsRepository: IUserDetailsRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getUserDetails(userId: String): UserDetailsDTO {
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("UserDetails")
                    .select() {
                        filter {
                            eq("userId", userId)
                        }
                    }.decodeSingle<UserDetailsDTO>()
                result
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createUserDetails(userId: String, name: String, surname: String): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val userDetailsDTO = UserDetailsDTO(
                    userId = userId,
                    name = name,
                    surname = surname
                )
                postgrest.from("UserDetails").insert(userDetailsDTO)
                true
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }
}