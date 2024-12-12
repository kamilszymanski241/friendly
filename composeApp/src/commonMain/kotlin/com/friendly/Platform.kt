package com.friendly

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.NavController
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun decodeByteArrayToBitMap(byteArray: ByteArray): ImageBitmap?

@Composable
expect fun CapturePhoto(navController: NavController, onSelect: (String) -> Unit)

expect fun convertBitMapToBase64(bitmap: ImageBitmap): String

expect fun convertBase64ToBitMap(base64: String): ImageBitmap