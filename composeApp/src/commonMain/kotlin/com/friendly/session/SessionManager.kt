package com.friendly.session

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.decodeByteArrayToBitMap
import com.friendly.models.UserDetails
import com.friendly.repositories.IStorageRepository
import com.friendly.repositories.IUserDetailsRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SessionManager: KoinComponent, ISessionManager {

    private val auth: Auth by inject()

    private val userDetailsRepository: IUserDetailsRepository by inject()
    private val storageRepository: IStorageRepository by inject()

    private val _sessionStatus = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    override val sessionStatus: StateFlow<SessionStatus> = _sessionStatus.asStateFlow()

    private val _currentUser = MutableStateFlow(auth.currentSessionOrNull()?.user)
    override val currentUser: StateFlow<UserInfo?> = _currentUser.asStateFlow()

    private val _currentUserDetails = MutableStateFlow<UserDetails?>(null)
    override val currentUserDetails: StateFlow<UserDetails?> = _currentUserDetails.asStateFlow()

    private val _userDetailsStatus = MutableStateFlow(UserDetailsStatus.Initializing)
    override val userDetailsStatus: StateFlow<UserDetailsStatus> = _userDetailsStatus.asStateFlow()

    private val _userProfilePicture = MutableStateFlow<ImageBitmap?>(null)
    override val userProfilePicture: StateFlow<ImageBitmap?> = _userProfilePicture.asStateFlow()

    private val _userProfilePictureStatus = MutableStateFlow(UserDetailsStatus.Initializing)
    override val userProfilePictureStatus: StateFlow<UserDetailsStatus> = _userProfilePictureStatus.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            auth.sessionStatus.collect {
                _sessionStatus.value = it
                when (it) {
                    is SessionStatus.Authenticated -> {
                        _currentUser.value = it.session.user
                        initUserDetails()
                        fetchProfilePicture()
                    }
                    is SessionStatus.NotAuthenticated -> {
                        _currentUser.value = null
                        if(it.isSignOut){
                            _currentUserDetails.value = null
                            _userDetailsStatus.value = UserDetailsStatus.Initializing
                            _userProfilePicture.value = null
                            _userProfilePictureStatus.value = UserDetailsStatus.Initializing
                        }
                    }
                    SessionStatus.Initializing -> {}
                    is SessionStatus.RefreshFailure -> {}
                }
            }
        }
    }
    override suspend fun initUserDetails(){
        try {
            _currentUserDetails.value = userDetailsRepository.getUserDetails(_currentUser.value!!.id).asDomainModel()
            _userDetailsStatus.value = UserDetailsStatus.Success
        } catch (e: Exception) {
            _userDetailsStatus.value = UserDetailsStatus.Failed
            println(e.message)
            //TODO()
        }
    }

    override suspend fun fetchProfilePicture(){
        try {
            _userProfilePicture.value = storageRepository.fetchProfilePicture(currentUser.value!!.id)
            _userProfilePictureStatus.value = UserDetailsStatus.Success
        }
        catch(e: Exception)
        {
            _userProfilePictureStatus.value = UserDetailsStatus.Failed
            println(e.message)
            //TODO()
        }
    }

    override fun setUserDetails(userDetails: UserDetails?)
    {
        _currentUserDetails.value = userDetails
    }
    override fun setUserDetailsStatus(userDetailsStatus: UserDetailsStatus)
    {
        _userDetailsStatus.value = userDetailsStatus
    }
    override fun setUserProfilePicture(profilePicture: ImageBitmap)
    {
        _userProfilePicture.value = profilePicture
    }
}