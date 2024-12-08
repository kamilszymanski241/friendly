package com.friendly.session

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.models.UserDetails
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface ISessionManager {
    val sessionStatus: StateFlow<SessionStatus>
    val currentUser: StateFlow<UserInfo?>
    val currentUserDetails: StateFlow<UserDetails?>
    val userDetailsStatus: StateFlow<UserDetailsStatus>
    val userProfilePicture: StateFlow<ImageBitmap?>
    fun setUserDetails(userDetails: UserDetails?)
    fun setUserDetailsStatus(userDetailsStatus: UserDetailsStatus)
    suspend fun fetchProfilePicture()
    suspend fun initUserDetails()
}