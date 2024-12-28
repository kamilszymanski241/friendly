package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import com.friendly.dtos.UserDetailsDTO
import com.friendly.managers.IRegistrationManager
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IUserDetailsRepository
import com.friendly.managers.ISessionManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FillUserDetailsViewModel: ViewModel(), KoinComponent {

    private val registrationManager: IRegistrationManager by inject()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onSurnameChange(surname: String) {
        _surname.value = surname
    }

    fun onContinue(): Boolean {
        if (_name.value == "" || _surname.value == "") {
            _errorMessage.value = "All fields must be filled"
            return false
        } else {
            registrationManager.updateUserDetails(_name.value, _surname.value)
            return true
        }
    }
}
