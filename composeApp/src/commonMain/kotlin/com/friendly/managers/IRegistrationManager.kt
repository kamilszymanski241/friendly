package com.friendly.managers

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.dtos.UserDetailsDTO
import kotlinx.coroutines.flow.StateFlow

interface IRegistrationManager {
    val userProfilePicture: StateFlow<ImageBitmap?>
    val email: StateFlow<String>
    val password: StateFlow<String>
    val surname: StateFlow<String>
    val name: StateFlow<String>
    val dateOfBirth: StateFlow<String>
    suspend fun registerUser(): Boolean
    fun updateEmail(newEmail: String)
    fun updatePassword(newPassword: String)
    fun updateProfilePicture(picture: ImageBitmap?)
    fun updateUserDetails(newName: String, newSurname: String)
}