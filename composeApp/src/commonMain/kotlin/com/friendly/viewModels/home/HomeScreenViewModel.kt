package com.friendly.viewModels.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import com.friendly.managers.ISessionManager
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeScreenViewModel: ViewModel(), KoinComponent {
    val sessionManager: ISessionManager by inject()

    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus
}