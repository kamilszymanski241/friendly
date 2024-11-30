package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.repositories.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpViewModel: ViewModel(), KoinComponent {

    private val authRepository: IAuthRepository by inject()

    private val _email = MutableStateFlow("")
    val email: Flow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _passwordRepeat = MutableStateFlow("")
    val passwordRepeat = _passwordRepeat

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onPasswordRepeatChange(password: String) {
        _passwordRepeat.value = password
    }

    fun onSignUp() {
        viewModelScope.launch {
            if(password.value == passwordRepeat.value)
            {
                try {
                    authRepository.signUp(
                        email = _email.value,
                        password = _password.value
                    )
                }
                catch (e: Exception)
                {
                    _errorMessage.value = e.message
                }
            }
           else{
               _errorMessage.value = "Passwords must be the same"
            }
        }
    }
}