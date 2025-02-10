package com.friendly.viewModels.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreenTopBarViewModel: ViewModel(), KoinComponent {
    private val sessionManager: ISessionManager by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()

    val user: StateFlow<UserInfo?> = sessionManager.currentUser
    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus

    private var _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    fun fetchUserDetails(){
        viewModelScope.launch {
            val userId = user.value?.id
            if(userId != null) {
                _userDetails.value =
                    userDetailsRepository.getUserDetails(userId).asDomainModel()
            }
        }
    }
}