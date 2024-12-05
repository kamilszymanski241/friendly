package com.friendly.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDetailsViewModel: ViewModel() {
    private val _name = MutableStateFlow("")
    val name: Flow<String> = _name

    private val _surname = MutableStateFlow("")
    val surname: Flow<String> = _surname

    private val _errorMessage = MutableStateFlow<String?>("")
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onNameChange(name: String) {
        _name.value = name
    }

    fun onSurnameChange(surname: String) {
        _surname.value = surname
    }

    fun onContinue(){

    }

}