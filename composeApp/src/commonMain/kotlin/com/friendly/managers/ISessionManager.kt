package com.friendly.managers

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.dtos.UserDetailsDTO
import com.friendly.models.UserDetails
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface ISessionManager {
    val sessionStatus: StateFlow<SessionStatus>
    val currentUser: StateFlow<UserInfo?>
}