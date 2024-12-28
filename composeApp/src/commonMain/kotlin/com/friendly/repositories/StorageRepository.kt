package com.friendly.repositories

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.compressBitmapToDesiredSize
import com.friendly.decodeBitMapToByteArray
import com.friendly.decodeByteArrayToBitMap
import com.friendly.resizeImageBitmapWithAspectRatio
import io.github.jan.supabase.storage.Storage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageRepository: IStorageRepository, KoinComponent {
    private val storage: Storage by inject()

    override suspend fun createUserBucket(userId: String) {
        storage.createBucket(id = userId)
    }

    override suspend fun uploadAProfilePicture(userId: String, picture: ImageBitmap): Boolean {
        val bucket = storage.from("profilePictures")
        return try {
            val resizedPicture = resizeImageBitmapWithAspectRatio(picture, 1000)
            val pictureAsByteArray = decodeBitMapToByteArray(resizedPicture)
            bucket.upload("$userId.jpg", pictureAsByteArray)
            true
        } catch (e: Exception) {
            throw e
        }
    }

/*    override suspend fun fetchProfilePicture(userId: String): String {
        val bucket = storage.from("profilePictures")
        *//*        return try {
            decodeByteArrayToBitMap(bucket.downloadPublic("$userId.jpg"))!!
        } catch (e: Exception) {
            return try {
                decodeByteArrayToBitMap(bucket.downloadPublic("default.jpg"))!!
            } catch (e: Exception) {
                throw e
            }
        }*//*
        return try {
            bucket.publicUrl("$userId.jpg")
        } catch (e: Exception) {
            return try {
                bucket.publicUrl("default.jpg")
            } catch (e: Exception) {
                throw e
            }
        }
    }*/
}