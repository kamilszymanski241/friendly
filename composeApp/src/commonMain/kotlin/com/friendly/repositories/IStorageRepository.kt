package com.friendly.repositories

import androidx.compose.ui.graphics.ImageBitmap

interface IStorageRepository {
    suspend fun createUserBucket(userId: String)
    suspend fun uploadAProfilePicture(userId: String, picture: ImageBitmap):Boolean
    suspend fun fetchProfilePicture(userId: String): ImageBitmap
}