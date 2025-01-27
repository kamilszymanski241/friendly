package com.friendly.viewModels.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.repositories.IAuthRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppSettingsScreenViewModel: KoinComponent, ViewModel() {

    private val authRepository: IAuthRepository by inject()

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