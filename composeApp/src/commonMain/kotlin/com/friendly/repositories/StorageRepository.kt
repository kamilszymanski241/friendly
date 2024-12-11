package com.friendly.repositories

import androidx.compose.ui.graphics.ImageBitmap
import com.friendly.decodeByteArrayToBitMap
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.storage.BucketApi
import io.github.jan.supabase.storage.BucketBuilder
import io.github.jan.supabase.storage.Storage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageRepository: IStorageRepository, KoinComponent {
    private val storage: Storage by inject()

    override suspend fun createUserBucket(userId: String) {
        if (userId != null) {
            storage.createBucket(id = userId)
        } else {
            println("User is not signed in!")
        }
    }

    override suspend fun uploadAProfilePicture(userId: String, picture: ByteArray) {
        val bucket: BucketApi
        try {
            bucket = storage.from(userId)
            try {
                bucket.upload("profilePicture", picture)
            } catch (e: Exception) {
                println(e.message)
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    override suspend fun fetchProfilePicture(userId: String): ImageBitmap {
        val bucket = storage.from("profilePictures")
        return try {
            decodeByteArrayToBitMap(bucket.downloadPublic("$userId.jpg"))!!
        } catch (e: Exception) {
            return try {
                decodeByteArrayToBitMap(bucket.downloadPublic("default.jpg"))!!
            } catch (e: Exception) {
                throw e
            }
        }
    }
}