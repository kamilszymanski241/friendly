package com.friendly.repositories

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.compressBitmapToDesiredSize
import com.friendly.decodeBitMapToByteArray
import com.friendly.decodeByteArrayToBitMap
import com.friendly.resizeImageBitmapWithAspectRatio
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.uploadAsFlow
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageRepository: IStorageRepository, KoinComponent {
    private val storage: Storage by inject()

    override suspend fun uploadAProfilePicture(userId: String, picture: ImageBitmap): Boolean {
        val bucket = storage.from("profilePictures")
        return try {
            val resizedPicture = resizeImageBitmapWithAspectRatio(picture, 1000)
            val pictureAsByteArray = decodeBitMapToByteArray(resizedPicture)
            bucket.uploadAsFlow("$userId.jpg", pictureAsByteArray).first{it is UploadStatus.Success}
            true
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun uploadEventPicture(eventId: String, picture: ImageBitmap): Boolean {
        val bucket = storage.from("eventPictures")
        return try {
            val resizedPicture = resizeImageBitmapWithAspectRatio(picture, 1000)
            val pictureAsByteArray = decodeBitMapToByteArray(resizedPicture)
            bucket.uploadAsFlow("$eventId.jpg", pictureAsByteArray).first{it is UploadStatus.Success}
            true
        } catch (e: Exception) {
            throw e
        }
    }
}