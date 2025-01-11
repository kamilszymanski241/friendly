package com.friendly.viewModels.createEvent

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FillBasicEventDetailsScreenViewModel (): ViewModel(){

    private val _title = MutableStateFlow("")
    val title: Flow<String> = _title

    private val _description = MutableStateFlow("")
    val description: Flow<String> = _description

    private val _maxParticipants = MutableStateFlow(0)
    val maxParticipants: Flow<Int> = _maxParticipants

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: Flow<String?> = _errorMessage

    private val _eventPicture = MutableStateFlow<ImageBitmap?>(null)
    val eventPicture: StateFlow<ImageBitmap?> = _eventPicture.asStateFlow()

    fun onTitleChange(name: String) {
        _title.value = name
    }

    fun onDescriptionChange(surname: String) {
        _description.value = surname
    }

    fun setPicture(picture: ImageBitmap?){
        _eventPicture.value = picture
    }
}

/*    private val _country = MutableStateFlow("")
    val country: Flow<String> = _country

        private val _city = MutableStateFlow("")
        val city: Flow<String> = _city

        private val _postalCode = MutableStateFlow("")
        val postalCode: Flow<String> = _postalCode

        private val _address = MutableStateFlow("")
        val address: Flow<String> = _address*/