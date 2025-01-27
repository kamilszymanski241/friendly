package com.friendly.repositories

import com.friendly.dtos.UserDetailsDTO
import com.friendly.models.Gender

interface IUserDetailsRepository {

    suspend fun getUserDetails(id: String): UserDetailsDTO

    suspend fun createUserDetails(userDetailsDTO: UserDetailsDTO): Boolean

    suspend fun changeName(userId: String, name: String): Boolean

    suspend fun changeSurname(userId: String, surname: String): Boolean

    suspend fun changeDateOfBirth(userId: String, dateOfBirth: String): Boolean

    suspend fun changeGender(userId: String, gender: Gender): Boolean

    suspend fun changeDescription(userId: String, description: String): Boolean
}