package com.friendly.repositories

interface IAuthRepository {
    suspend fun signIn (email: String, password: String): Boolean
    suspend fun signUp (email: String, password: String): Boolean
}