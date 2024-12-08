package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.models.UserDetails
import com.friendly.repositories.IAuthRepository
import com.friendly.session.ISessionManager
import com.friendly.session.UserDetailsStatus
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileViewModel: ViewModel(), KoinComponent {
    private val authRepository: IAuthRepository by inject()
    private val sessionManager: ISessionManager by inject()

    val user: StateFlow<UserInfo?> = sessionManager.currentUser
    val userDetails: StateFlow<UserDetails?> = sessionManager.currentUserDetails
    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus
    val userDetailsStatus: StateFlow<UserDetailsStatus> = sessionManager.userDetailsStatus

    fun onSignOut(){
        viewModelScope.launch {
            try {
                authRepository.signOut()
            }
            catch (e: Exception) {
                println(e.message)
            }
        }
    }
}