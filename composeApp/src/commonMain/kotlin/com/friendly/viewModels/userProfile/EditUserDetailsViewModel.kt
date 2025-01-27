package com.friendly.viewModels.userProfile

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.Gender
import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditUserDetailsViewModel: ViewModel(), KoinComponent {
    private val sessionManager: ISessionManager by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()

    private var _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private var _updated = MutableStateFlow(false)
    val updated: StateFlow<Boolean> = _updated

    init{
        fetchUserDetails()
    }

    fun fetchUserDetails(){
        viewModelScope.launch {
            _userDetails.value =
                userDetailsRepository.getUserDetails(sessionManager.currentUser.value!!.id)
                    .asDomainModel()
            _updated.value = false
        }
    }

    fun changeName(name: String){
        _userDetails.value = null
        viewModelScope.launch {
            try {
                userDetailsRepository.changeName(sessionManager.currentUser.value!!.id, name)
                _updated.value = true
            }catch(e: Exception){
                println("Couldn't change name: ${e.message}")
            }
        }
    }
    fun changeSurname(surname: String){
        _userDetails.value = null
        viewModelScope.launch {
            try {
                userDetailsRepository.changeSurname(sessionManager.currentUser.value!!.id, surname)
                _updated.value = true
            }catch(e: Exception){
                println("Couldn't change surname: ${e.message}")
            }
        }
    }
    fun changeDateOfBirth(dateOfBirth: String){
        _userDetails.value = null
        viewModelScope.launch {
            try {
                userDetailsRepository.changeDateOfBirth(sessionManager.currentUser.value!!.id, dateOfBirth)
                _updated.value = true
            }catch(e: Exception){
                println("Couldn't change surname: ${e.message}")
            }
        }
    }
    fun changeGender(gender: Gender){
        _userDetails.value = null
        viewModelScope.launch {
            try {
                userDetailsRepository.changeGender(sessionManager.currentUser.value!!.id, gender)
                _updated.value = true
            }catch(e: Exception){
                println("Couldn't change surname: ${e.message}")
            }
        }
    }
    fun changeDescription(description: String){
        _userDetails.value = null
        viewModelScope.launch {
            try {
                userDetailsRepository.changeDescription(sessionManager.currentUser.value!!.id, description)
                _updated.value = true
            }catch(e: Exception){
                println("Couldn't change description: ${e.message}")
            }
        }
    }
}