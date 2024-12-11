package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.models.UserDetails
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IUserDetailsRepository
import com.friendly.session.ISessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FillUserDetailsViewModel: ViewModel(), KoinComponent {

    private val userDetailsRepository: IUserDetailsRepository by inject()

    private val sessionManager: ISessionManager by inject()

    private val authRepository: IAuthRepository by inject()

    private val _name = MutableStateFlow("")
    val name: Flow<String> = _name

    private val _surname = MutableStateFlow("")
    val surname: Flow<String> = _surname


    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _success = MutableStateFlow<Boolean>(value = false)
    val success = _success


    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onSurnameChange(surname: String) {
        _surname.value = surname
    }

    fun onContinue() {
        viewModelScope.launch {
            try {
                if (userDetailsRepository.createUserDetails(
                        id = sessionManager.currentUser.value!!.id,
                        name = _name.value,
                        surname = _surname.value
                    )
                ) {
                    _success.value = true
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
        sessionManager.setUserDetails(
            UserDetails(
                id = "",
                joined = "",
                name = _name.value,
                surname = _surname.value
            )
        )
    }
}