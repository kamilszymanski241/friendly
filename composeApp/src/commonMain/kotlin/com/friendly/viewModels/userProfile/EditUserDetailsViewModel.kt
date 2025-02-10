package com.friendly.viewModels.userProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.friendly.managers.ISessionManager
import com.friendly.models.Gender
import com.friendly.models.UserDetails
import com.friendly.repositories.IUserDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class EditUserDetailsViewModel: ViewModel(), KoinComponent {

    private val sessionManager: ISessionManager by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()

    private var _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init{
        fetchUserDetails()
    }
    fun refresh(){
        _userDetails.value = null
        fetchUserDetails()
    }
    fun setErrorMessage(message: String){
        _errorMessage.value = message
    }
    private fun fetchUserDetails(){
        try {
            val id = sessionManager.currentUser.value?.id ?: throw Exception()
            viewModelScope.launch {
                _userDetails.value =
                    userDetailsRepository.getUserDetails(id)
                        .asDomainModel()
            }
        }catch(e: Exception){
            setErrorMessage("Something went wrong")
        }
    }
    fun changeName(name: String){
        viewModelScope.launch {
            try {
                val id = sessionManager.currentUser.value?.id ?: throw Exception()
                userDetailsRepository.changeName(id, name)
                refresh()
            }catch(e: Exception){
                setErrorMessage("Something went wrong")
            }
        }
    }
    fun changeSurname(surname: String){
        viewModelScope.launch {
            try {
                val id = sessionManager.currentUser.value?.id ?: throw Exception()
                userDetailsRepository.changeSurname(id, surname)
                refresh()
            }catch(e: Exception){
                setErrorMessage("Something went wrong")
            }
        }
    }
    fun changeDateOfBirth(dateOfBirth: String){
        viewModelScope.launch {
            try {
                val id = sessionManager.currentUser.value?.id ?: throw Exception()
                userDetailsRepository.changeDateOfBirth(id, dateOfBirth)
                refresh()
            }catch(e: Exception){
                setErrorMessage("Something went wrong")
            }
        }
    }
    fun changeGender(gender: Gender){
        viewModelScope.launch {
            try {
                val id = sessionManager.currentUser.value?.id ?: throw Exception()
                userDetailsRepository.changeGender(id, gender)
                refresh()
            }catch(e: Exception){
                setErrorMessage("Something went wrong")
            }
        }
    }
    fun changeDescription(description: String){
        viewModelScope.launch {
            try {
                val id = sessionManager.currentUser.value?.id ?: throw Exception()
                userDetailsRepository.changeDescription(id, description)
                refresh()
            }catch(e: Exception){
                setErrorMessage("Something went wrong")
            }
        }
    }
}