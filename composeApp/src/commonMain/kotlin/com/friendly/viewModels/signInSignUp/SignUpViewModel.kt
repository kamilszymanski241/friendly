package com.friendly.viewModels.signInSignUp

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.dtos.UserDetailsDTO
import com.friendly.helpers.DateTimeHelper
import com.friendly.managers.ISessionManager
import com.friendly.models.Gender
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IStorageRepository
import com.friendly.repositories.IUserDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SignUpViewModel: ViewModel(), KoinComponent {

    private val authRepository: IAuthRepository by inject()
    private val storageRepository: IStorageRepository by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()
    private val sessionManager: ISessionManager by inject()

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _surname = MutableStateFlow("")
    val surname: StateFlow<String> = _surname

    private val _dateOfBirth = MutableStateFlow<String?>(null)
    val dateOfBirth: Flow<String?> = _dateOfBirth

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _gender = MutableStateFlow<Gender?>(null)
    val gender: StateFlow<Gender?> = _gender

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    val password = _password

    private val _passwordRepeat = MutableStateFlow("")
    val passwordRepeat = _passwordRepeat

    private val _success = MutableStateFlow(value = false)
    val success = _success

    private val _loading = MutableStateFlow(value = false)
    val loading = _loading

    private val _userProfilePicture = MutableStateFlow<ImageBitmap?>(null)
    val userProfilePicture: StateFlow<ImageBitmap?> = _userProfilePicture.asStateFlow()

    fun setErrorMessage(message: String){
        _errorMessage.value = message
    }

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onSurnameChange(surname: String) {
        _surname.value = surname
    }

    fun onDescriptionChange(description: String) {
        _description.value = description
    }

    fun onDateOfBirthChange(date: String) {
        _dateOfBirth.value = date
    }

    fun onGenderChange(gender: Gender) {
        _gender.value = gender
    }

    fun onEmailChange(email: String) {
        _email.value = email
    }

    fun onPasswordChange(password: String) {
        _password.value = password
    }

    fun onPasswordRepeatChange(password: String) {
        _passwordRepeat.value = password
    }

    fun onPictureChange(picture: ImageBitmap?){
        _userProfilePicture.value = picture
    }
    fun onContinueToProfilePic(): Boolean {
        if (_name.value == "" || _surname.value == "" || _gender.value == null || _dateOfBirth.value == null) {
            _errorMessage.value = "All fields must be filled"
            return false
        } else {
            return true
        }
    }

    fun onContinueToEmailAndPassword(): Boolean {
        (if (_userProfilePicture.value != null) {
            _errorMessage.value = ""
            return true
        } else {
            _errorMessage.value = "Profile picture is mandatory"
            return false
        })
    }

    fun onSignUp() {
        if (password.value == passwordRepeat.value) {
            loading.value = true
            viewModelScope.launch {
                try {
                    if (authRepository.signUp(
                            email = _email.value,
                            password = _password.value
                        )
                    ) {
                        if (userDetailsRepository.createUserDetails(
                                UserDetailsDTO(
                                    id = sessionManager.currentUser.value!!.id,
                                    name = _name.value,
                                    surname = _surname.value,
                                    dateOfBirth = _dateOfBirth.value!!,
                                    description = _description.value,
                                    gender = _gender.value!!
                                )
                            )
                        ) {
                            if (storageRepository.uploadAProfilePicture(
                                    sessionManager.currentUser.value!!.id,
                                    _userProfilePicture.value!!
                                )
                            ) {
                                success.value = true
                            }
                        }
                    }
                } catch (e: Exception) {
                    println("Couldn't register: ${e.message}")
                    _errorMessage.value = "Couldn't register: ${e.message}"
                }
            }
        } else {
            _errorMessage.value = "Passwords must be the same"
        }
    }
}