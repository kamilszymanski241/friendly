package com.friendly.repositories

import com.friendly.dtos.UserDetailsDTO
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserDetailsRepository: IUserDetailsRepository, KoinComponent {

    private val postgrest: Postgrest by inject()

    override suspend fun getUserDetails(id: String): UserDetailsDTO {
        return try {
            withContext(Dispatchers.IO) {
                val result = postgrest.from("UserDetails")
                    .select() {
                        filter {
                            eq("id", id)
                        }
                    }.decodeSingle<UserDetailsDTO>()
                result
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createUserDetails(userDetailsDTO: UserDetailsDTO): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                postgrest.from("UserDetails").insert(userDetailsDTO)
            }
            true
        } catch (e: Exception) {
            throw e
        }
    }
}