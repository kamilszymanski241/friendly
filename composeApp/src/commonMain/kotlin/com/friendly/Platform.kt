package com.friendly

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun decodeByteArrayToBitMap(byteArray: ByteArray): ImageBitmap?

expect fun decodeBitMapToByteArray(bitmap: ImageBitmap): ByteArray

expect fun decodeBitMapToBase64(bitmap: ImageBitmap): String

expect fun decodeBase64ToBitMap(base64: String): ImageBitmap

expect fun cropBitmapToSquare(original: ImageBitmap): ImageBitmap

expect fun compressBitmapToDesiredSize(original: ImageBitmap, maxSizeInKB: Int): ImageBitmap

expect fun resizeImageBitmapWithAspectRatio(original: ImageBitmap, maxDimension: Int): ImageBitmap

@Composable
expect fun CapturePhoto(onSelect: (ImageBitmap) -> Unit, onClose: ()-> Unit)

@Composable
expect fun PickPhoto(onSelect: (ImageBitmap) ->Unit, onClose: ()->Unit)

@Composable
expect fun SelectLocation(
    onSelect: (Pair<Double, Double>, String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
)

@Composable
expect fun ShowStaticMap(modifier: Modifier = Modifier, coordinates: Pair<Double,Double>, zoom: Float)

expect val nativeModule: Module
