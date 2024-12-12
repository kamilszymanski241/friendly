package com.friendly.viewModels

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent

class CollectPictureViewModel: ViewModel(), KoinComponent {

    private val _userProfilePicture = MutableStateFlow<ImageBitmap?>(null)
    val userProfilePicture: StateFlow<ImageBitmap?> = _userProfilePicture.asStateFlow()

    fun setPicture(picture: ImageBitmap?){
        _userProfilePicture.value = picture
    }

    fun onContinue()
    {

    }
}