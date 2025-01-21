package com.friendly.managers

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.dtos.UserDetailsDTO
import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionManager: KoinComponent, ISessionManager {

    private val auth: Auth by inject()

    private val userDetailsRepository: IUserDetailsRepository by inject()

    private val _sessionStatus = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    override val sessionStatus: StateFlow<SessionStatus> = _sessionStatus.asStateFlow()

    private val _currentUser = MutableStateFlow(auth.currentSessionOrNull()?.user)
    override val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()

    private val _currentUserDetails = MutableStateFlow<UserDetails?>(null)
    override val currentUserDetails: StateFlow<UserDetails?> = _currentUserDetails.asStateFlow()

    private val _userDetailsStatus = MutableStateFlow(UserDetailsStatus.Initializing)
    override val userDetailsStatus: StateFlow<UserDetailsStatus> = _userDetailsStatus.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            auth.sessionStatus.collect {
                _sessionStatus.value = it
                when (it) {
                    is SessionStatus.Authenticated -> {
                        _currentUser.value = it.session.user
                        initUserDetails()
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _currentUser.value = null
                        if(it.isSignOut){
                            _currentUserDetails.value = null
                            _userDetailsStatus.value = UserDetailsStatus.Initializing
                        }
                    }
                    SessionStatus.Initializing -> {}
                    is SessionStatus.RefreshFailure -> {}
                }
            }
        }
    }
    override suspend fun initUserDetails(){
        try {
            _currentUserDetails.value = userDetailsRepository.getUserDetails(currentUser.value!!.id).asDomainModel()
            _userDetailsStatus.value = UserDetailsStatus.Success
        } catch (e: Exception) {
            _userDetailsStatus.value = UserDetailsStatus.Failed
            //TODO()
        }
    }
}