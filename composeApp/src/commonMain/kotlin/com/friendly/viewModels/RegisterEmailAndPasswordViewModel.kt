package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.IRegistrationManager
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IStorageRepository
import com.friendly.managers.ISessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegisterEmailAndPasswordViewModel: KoinComponent, ViewModel() {

    private val registrationManager: IRegistrationManager by inject()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _passwordRepeat = MutableStateFlow("")
    val passwordRepeat = _passwordRepeat

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

    fun onPasswordRepeatChange(password: String) {
        _passwordRepeat.value = password
    }

    fun onSignUp() {
        viewModelScope.launch {
            if(password.value == passwordRepeat.value)
            {
                registrationManager.updateEmail(_email.value)
                registrationManager.updatePassword(_password.value)
                try {
                    registrationManager.registerUser()
                    success.value = true
                }
                catch (e: Exception)
                {
                    println(e.message)
                    _errorMessage.value = e.message
                }
            }
            else{
                _errorMessage.value = "Passwords must be the same"
            }
        }
    }
}