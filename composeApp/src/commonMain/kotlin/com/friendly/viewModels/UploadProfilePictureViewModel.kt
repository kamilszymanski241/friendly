package com.friendly.viewModels

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.friendly.managers.IRegistrationManager
import com.friendly.managers.ISessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UploadProfilePictureViewModel: ViewModel(), KoinComponent {

    private val registrationManager: IRegistrationManager by inject()

    private val _userProfilePicture = MutableStateFlow<ImageBitmap?>(null)
    val userProfilePicture: StateFlow<ImageBitmap?> = _userProfilePicture.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun setPicture(picture: ImageBitmap?){
        _userProfilePicture.value = picture
    }

    fun setErrorMessage(message: String){
        _errorMessage.value = message
    }

    fun onContinue(): Boolean {
        (if (_userProfilePicture.value != null) {
            registrationManager.updateProfilePicture(_userProfilePicture.value)
            _errorMessage.value = ""
            return true
        } else {
            _errorMessage.value = "Profile picture is mandatory"
            return false
        })
    }
}