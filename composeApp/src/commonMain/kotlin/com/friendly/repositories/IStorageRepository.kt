package com.friendly.repositories

import androidx.compose.ui.graphics.ImageBitmap

interface IStorageRepository {
    suspend fun uploadAProfilePicture(userId: String, picture: ImageBitmap):Boolean
    suspend fun uploadEventPicture(eventId: String, picture: ImageBitmap): Boolean
    suspend fun upsertProfilePicture(userId: String, picture: ImageBitmap): Boolean
    suspend fun upsertEventPicture(eventId: String, picture: ImageBitmap): Boolean
}