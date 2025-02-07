package com.friendly.viewModels.userProfile

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

    init{
        fetchUserDetails()
    }
    fun refresh(){
        _userDetails.value = null
        fetchUserDetails()
    }
    private fun fetchUserDetails(){
        try {
            viewModelScope.launch {
                _userDetails.value =
                    userDetailsRepository.getUserDetails(sessionManager.currentUser.value!!.id)
                        .asDomainModel()
            }
        }catch(e: Exception){
            println("Couldn't fetch user details: ${e.message}")
        }
    }
    fun changeName(name: String){
        viewModelScope.launch {
            try {
                userDetailsRepository.changeName(sessionManager.currentUser.value!!.id, name)
                refresh()
            }catch(e: Exception){
                println("Couldn't change name: ${e.message}")
            }
        }
    }
    fun changeSurname(surname: String){
        viewModelScope.launch {
            try {
                userDetailsRepository.changeSurname(sessionManager.currentUser.value!!.id, surname)
                refresh()
            }catch(e: Exception){
                println("Couldn't change surname: ${e.message}")
            }
        }
    }
    fun changeDateOfBirth(dateOfBirth: String){
        viewModelScope.launch {
            try {
                userDetailsRepository.changeDateOfBirth(sessionManager.currentUser.value!!.id, dateOfBirth)
                refresh()
            }catch(e: Exception){
                println("Couldn't change surname: ${e.message}")
            }
        }
    }
    fun changeGender(gender: Gender){
        viewModelScope.launch {
            try {
                userDetailsRepository.changeGender(sessionManager.currentUser.value!!.id, gender)
                refresh()
            }catch(e: Exception){
                println("Couldn't change gender: ${e.message}")
            }
        }
    }
    fun changeDescription(description: String){
        viewModelScope.launch {
            try {
                userDetailsRepository.changeDescription(sessionManager.currentUser.value!!.id, description)
                refresh()
            }catch(e: Exception){
                println("Couldn't change description: ${e.message}")
            }
        }
    }
}