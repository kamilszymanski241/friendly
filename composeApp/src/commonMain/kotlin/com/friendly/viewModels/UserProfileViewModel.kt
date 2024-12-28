package com.friendly.viewModels

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.friendly.models.UserDetails
import com.friendly.managers.ISessionManager
import com.friendly.managers.UserDetailsStatus
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileViewModel: ViewModel(), KoinComponent {

    private val sessionManager: ISessionManager by inject()

    val user: StateFlow<UserInfo?> = sessionManager.currentUser
    val userDetails: StateFlow<UserDetails?> = sessionManager.currentUserDetails
    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus
    val userDetailsStatus: StateFlow<UserDetailsStatus> = sessionManager.userDetailsStatus

}