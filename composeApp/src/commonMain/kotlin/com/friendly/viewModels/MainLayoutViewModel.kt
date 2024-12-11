package com.friendly.viewModels

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.models.UserDetails
import com.friendly.repositories.IAuthRepository
import com.friendly.session.ISessionManager
import com.friendly.session.UserDetailsStatus
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainLayoutViewModel: ViewModel(), KoinComponent {
    private val sessionManager: ISessionManager by inject()

    val user: StateFlow<UserInfo?> = sessionManager.currentUser
    val userDetails: StateFlow<UserDetails?> = sessionManager.currentUserDetails
    val userProfilePicture: StateFlow<ImageBitmap?> = sessionManager.userProfilePicture
    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus
    val userDetailsStatus: StateFlow<UserDetailsStatus> = sessionManager.userDetailsStatus
    val userProfilePictureStatus: StateFlow<UserDetailsStatus> = sessionManager.userProfilePictureStatus
}