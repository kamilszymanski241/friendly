package com.friendly.repositories

import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface IAuthRepository {
    suspend fun signIn (email: String, password: String): Boolean
    suspend fun signUp (email: String, password: String): Boolean
    suspend fun signOut (): Boolean
    suspend fun updateEmail(emailReceived: String): Boolean
    suspend fun updatePassword(passwordReceived: String): Boolean
}