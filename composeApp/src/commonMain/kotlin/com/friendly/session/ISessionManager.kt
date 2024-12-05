package com.friendly.session

import com.friendly.models.UserDetails
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface ISessionManager {
    val sessionStatus: StateFlow<SessionStatus>
    val currentUser: StateFlow<UserInfo?>
    val currentUserDetails: StateFlow<UserDetails?>
}