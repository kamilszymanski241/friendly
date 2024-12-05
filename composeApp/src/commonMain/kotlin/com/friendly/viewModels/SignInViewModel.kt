package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_black
import com.friendly.generated.resources.friendly_logo_green
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IStorageRepository
import io.github.jan.supabase.auth.Auth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignInViewModel: ViewModel(), KoinComponent {
    private val authRepository: IAuthRepository by inject()

    private val auth: Auth by inject()

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _success = MutableStateFlow<Boolean>(value = false)
    val success = _success

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onSignIn() {
        viewModelScope.launch {
            try {
                if(authRepository.signIn(
                    email = _email.value,
                    password = _password.value
                ))
                {
                    _success.value = true
                }
            }
            catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}