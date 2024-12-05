package com.friendly.session

import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import io.ktor.util.valuesOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionManager: KoinComponent, ISessionManager{

    private val auth: Auth by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()

    private val _sessionStatus = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    override val sessionStatus: StateFlow<SessionStatus> = _sessionStatus.asStateFlow()

    private val _currentUser = MutableStateFlow(auth.currentSessionOrNull()?.user)
    override val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()

    private val _currentUserDetails = MutableStateFlow<UserDetails?>(null)
    override val currentUserDetails: StateFlow<UserDetails?> = _currentUserDetails.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            auth.sessionStatus.collect { status ->
                _sessionStatus.value = status
                if (status is SessionStatus.Authenticated) {
                    _currentUser.value = status.session.user
                    try {
                        _currentUserDetails.value = userDetailsRepository.getUserDetails(_currentUser.value!!.id).asDomainModel()
                    }catch(e: Exception)
                    {
                        println(e.message)
                    }
                } else if (status is SessionStatus.NotAuthenticated) {
                    _currentUser.value = null
                }
            }
        }
    }
}