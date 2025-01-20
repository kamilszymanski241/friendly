package com.friendly.managers

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.dtos.UserDetailsDTO
import com.friendly.models.Gender
import com.friendly.repositories.IAuthRepository
import com.friendly.repositories.IStorageRepository
import com.friendly.repositories.IUserDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegistrationManager: KoinComponent, IRegistrationManager {

    private val _userProfilePicture = MutableStateFlow<ImageBitmap?>(null)
    override val userProfilePicture: StateFlow<ImageBitmap?> = _userProfilePicture.asStateFlow()

    private val authRepository: IAuthRepository by inject()
    private val storageRepository: IStorageRepository by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()
    private val sessionManager: ISessionManager by inject()

    private val _name = MutableStateFlow("")
    override val name: StateFlow<String> = _name

    private val _surname = MutableStateFlow("")
    override val surname: StateFlow<String> = _surname

    private val _email = MutableStateFlow("")
    override val email: StateFlow<String> = _email

    private val _password = MutableStateFlow("")
    override val password: StateFlow<String> = _password

    override fun updateUserDetails(newName: String, newSurname: String) {
        _name.value = newName
        _surname.value = newSurname
    }

    override fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    override fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    override fun updateProfilePicture(picture: ImageBitmap?){
        _userProfilePicture.value = picture
    }

    override suspend fun registerUser(): Boolean {
        if (authRepository.signUp(
                email = _email.value,
                password = _password.value
            )
        ) {
            if (userDetailsRepository.createUserDetails(
                UserDetailsDTO(id = sessionManager.currentUser.value!!.id, name = _name.value, dateOfBirth = "", description = "", gender = Gender.Male, surname = _surname.value)
                )
            ) {
                if (storageRepository.uploadAProfilePicture(
                        sessionManager.currentUser.value!!.id,
                        _userProfilePicture.value!!
                    )
                ) {
                    updateUserDetails("","")
                    updateEmail("")
                    updatePassword("")
                    updateProfilePicture(null)
                    return true
                }
            }
        }
        return false
    }
}