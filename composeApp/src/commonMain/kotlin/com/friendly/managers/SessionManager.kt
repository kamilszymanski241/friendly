package com.friendly.managers

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionManager: KoinComponent, ISessionManager {

    private val auth: Auth by inject()

    private val _sessionStatus = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    override val sessionStatus: StateFlow<SessionStatus> = _sessionStatus.asStateFlow()

    private val _currentUser = MutableStateFlow(auth.currentSessionOrNull()?.user)
    override val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            auth.sessionStatus.collect {
                _sessionStatus.value = it
                when (it) {
                    is SessionStatus.Authenticated -> {
                        _currentUser.value = it.session.user
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _currentUser.value = null
                    }
                    SessionStatus.Initializing -> {}
                    is SessionStatus.RefreshFailure -> {}
                }
            }
        }
    }
}