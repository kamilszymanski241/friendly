package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.status.SessionStatus
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

    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus

    init{
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