package com.friendly.repositories

import com.friendly.DTOs.UserDetailsDTO

interface IUserDetailsRepository {

    suspend fun getUserDetails(userId: String): UserDetailsDTO

    suspend fun createUserDetails(userId: String, name: String, surname: String)
}