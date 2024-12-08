package com.friendly.repositories

interface IStorageRepository {
    suspend fun createUserBucket(userId: String?)
    suspend fun uploadAProfilePicture(userId: String?, picture: ByteArray)
    suspend fun fetchProfilePicture(userId: String?): ByteArray
}