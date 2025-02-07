package com.friendly.repositories

interface IAuthRepository {
    suspend fun signIn (email: String, password: String): Boolean
    suspend fun signUp (email: String, password: String): Boolean
    suspend fun signOut (): Boolean
    suspend fun updateEmail(email: String): Boolean
    suspend fun updatePassword(password: String): Boolean
}