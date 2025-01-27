package com.friendly.viewModels.userProfile

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.PlatformContext
import com.friendly.getAsyncImageLoader
import com.friendly.helpers.CacheHelper
import com.friendly.managers.ISessionManager
import com.friendly.models.UserDetails
import com.friendly.repositories.IStorageRepository
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.compose.KoinContext
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileViewModel(private val userId: String): ViewModel(), KoinComponent {

    private val sessionManager: ISessionManager by inject()
    private val userDetailsRepository: IUserDetailsRepository by inject()
    private val storageRepository: IStorageRepository by inject()
    private val cacheHelper: CacheHelper by inject()

    private var _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> = _userDetails

    private var _isSelfProfile = MutableStateFlow(false)
    val isSelfProfile: StateFlow<Boolean> = _isSelfProfile

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _showSignInReminder = MutableStateFlow(false)
    val showSignInReminder: Flow<Boolean> = _showSignInReminder

    val sessionStatus: StateFlow<SessionStatus> = sessionManager.sessionStatus

    fun initialize() {
        if (sessionStatus.value == SessionStatus.NotAuthenticated(false) || sessionStatus.value == SessionStatus.NotAuthenticated(
                true
            )
        ) {
            _isSelfProfile.value = false
            _showSignInReminder.value = true
        } else {
            if (sessionManager.currentUser.value != null) {
                if (userId == sessionManager.currentUser.value!!.id) {
                    _isSelfProfile.value = true
                    viewModelScope.launch {
                        _userDetails.value =
                            userDetailsRepository.getUserDetails(sessionManager.currentUser.value!!.id)
                                .asDomainModel()
                        cacheHelper.clearFromCacheByKey(_userDetails.value!!.profilePictureUrl)
                    }
                } else {
                    viewModelScope.launch {
                        _userDetails.value =
                            userDetailsRepository.getUserDetails(userId).asDomainModel()
                        cacheHelper.clearFromCacheByKey(_userDetails.value!!.profilePictureUrl)
                    }
                }
            }
        }
    }
    fun refresh(){
        _isRefreshing.value = true
        _userDetails.value = null
        initialize()
        _isRefreshing.value = false
    }
    fun changeProfilePicture(picture: ImageBitmap) {
        val pictureURL = _userDetails.value!!.profilePictureUrl
        _userDetails.value = null
            viewModelScope.launch {
                try {
                    if (storageRepository.upsertProfilePicture(
                            sessionManager.currentUser.value!!.id,
                            picture
                        )
                    ) {
                        cacheHelper.clearFromCacheByKey(pictureURL)
                        initialize()
                    }
                } catch (e: Exception) {
                    println("Couldn't update photo: ${e.message}")
                    initialize()
                }
            }
        }
    }