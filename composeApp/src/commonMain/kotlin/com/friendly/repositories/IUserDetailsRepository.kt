package com.friendly.repositories

import com.friendly.dtos.UserDetailsDTO

interface IUserDetailsRepository {

    suspend fun getUserDetails(id: String): UserDetailsDTO

    suspend fun createUserDetails(userDetailsDTO: UserDetailsDTO): Boolean

}