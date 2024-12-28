package com.friendly.viewModels

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.friendly.models.Event
import com.friendly.managers.ISessionManager
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UpcomingEventsScreenViewModel: ViewModel(), KoinComponent {
    private val sessionManager: ISessionManager by inject()

    val user: StateFlow<UserInfo?> = sessionManager.currentUser
    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus

    private val _showSignInReminder = MutableStateFlow(false)
    val showSignInReminder: Flow<Boolean> = _showSignInReminder

    private val _eventsList = MutableStateFlow<List<Pair<Event, List<ImageBitmap>>>?>(null)
    val eventsList: Flow<List<Pair<Event, List<ImageBitmap>>>?> = _eventsList

    init{
        if(sessionStatus.value == SessionStatus.NotAuthenticated(false) || sessionStatus.value == SessionStatus.NotAuthenticated(true))
        {
            _showSignInReminder.value = true
        }
        else{
            //TODO()
        }
    }
}