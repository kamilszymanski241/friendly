package com.friendly.repositories

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.compressBitmapToDesiredSize
import com.friendly.cropBitmapToPanorama
import com.friendly.cropBitmapToSquare
import com.friendly.decodeBitMapToByteArray
import com.friendly.decodeByteArrayToBitMap
import com.friendly.resizeImageBitmapWithAspectRatio
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.UploadStatus
import io.github.jan.supabase.storage.updateAsFlow
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
            val changedToSquare = cropBitmapToSquare(resizedPicture)
            val pictureAsByteArray = decodeBitMapToByteArray(changedToSquare)
            bucket.uploadAsFlow("$userId/profile.jpg", pictureAsByteArray).first{it is UploadStatus.Success}
            true
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun uploadEventPicture(eventId: String, picture: ImageBitmap): Boolean {
        val bucket = storage.from("eventPictures")
        return try {
            val resizedPicture = resizeImageBitmapWithAspectRatio(picture, 1000)
            val changedToPanorama = cropBitmapToPanorama(resizedPicture)
            val pictureAsByteArray = decodeBitMapToByteArray(changedToPanorama)
            bucket.uploadAsFlow("$eventId/event.jpg", pictureAsByteArray).first{it is UploadStatus.Success}
            true
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun upsertProfilePicture(userId: String, picture: ImageBitmap): Boolean{
        val bucket = storage.from("profilePictures")
        return try {
            val resizedPicture = resizeImageBitmapWithAspectRatio(picture, 1000)
            val changedToSquare = cropBitmapToSquare(resizedPicture)
            val pictureAsByteArray = decodeBitMapToByteArray(changedToSquare)
            bucket.updateAsFlow("$userId/profile.jpg", pictureAsByteArray){upsert = true}.first{it is UploadStatus.Success}
            true
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun upsertEventPicture(eventId: String, picture: ImageBitmap): Boolean{
        val bucket = storage.from("eventPictures")
        return try {
            val resizedPicture = resizeImageBitmapWithAspectRatio(picture, 1000)
            val changedToPanorama = cropBitmapToPanorama(resizedPicture)
            val pictureAsByteArray = decodeBitMapToByteArray(changedToPanorama)
            bucket.updateAsFlow("$eventId/event.jpg", pictureAsByteArray){upsert = true}.first{it is UploadStatus.Success}
            true
        } catch (e: Exception) {
            throw e
        }
    }
}