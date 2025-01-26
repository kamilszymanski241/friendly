package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileViewModel(private val userId: String): ViewModel(), KoinComponent {

    private val sessionManager: ISessionManager by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()

    private var _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private var _isSelfProfile = MutableStateFlow(false)
    val isSelfProfile: StateFlow<Boolean> = _isSelfProfile

    private val _showSignInReminder = MutableStateFlow(false)
    val showSignInReminder: Flow<Boolean> = _showSignInReminder

    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus

    init{
        if(sessionStatus.value == SessionStatus.NotAuthenticated(false) || sessionStatus.value == SessionStatus.NotAuthenticated(true))
        {
            _isSelfProfile.value = false
            _showSignInReminder.value = true
        }
        else{
            if(sessionManager.currentUser.value != null) {
                if (userId == sessionManager.currentUser.value!!.id){
                    _isSelfProfile.value = true
                    _userDetails.value = sessionManager.currentUserDetails.value
                }
                else{
                    viewModelScope.launch {
                        _userDetails.value = userDetailsRepository.getUserDetails(userId).asDomainModel()
                    }
                }
            }
        }
    }
}