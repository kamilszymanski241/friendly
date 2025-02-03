package com.friendly.viewModels.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.repositories.IAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppSettingsScreenViewModel: KoinComponent, ViewModel() {

    private val authRepository: IAuthRepository by inject()
    private val sessionManager: ISessionManager by inject()

    private var _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private var _emailChangeSuccess = MutableStateFlow(false)
    val emailChangeSuccess: StateFlow<Boolean> = _emailChangeSuccess

    private var _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    private var _emailResponseError = MutableStateFlow("")
    val emailResponseError: StateFlow<String> = _emailResponseError

    private var _passwordResponseError = MutableStateFlow("")
    val passwordResponseError: StateFlow<String> = _passwordResponseError

    init{
        if(sessionManager.currentUser.value != null)
        {
            _email.value = sessionManager.currentUser.value!!.email ?: ""
        }
    }
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
    fun onEmailChange(email: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                authRepository.updateEmail(email)
                _emailChangeSuccess.value = true
            } catch (e: Exception) {
                println(e.message)
                _emailResponseError.value = "Error occurred"
                _loading.value = false
            }
        }
    }
    fun onPasswordChange(password: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                authRepository.updatePassword(password)
                _emailChangeSuccess.value = true
            } catch (e: Exception) {
                println(e.message)
                _passwordResponseError.value = "Password must be different"
                _loading.value = false
            }
        }
    }
}