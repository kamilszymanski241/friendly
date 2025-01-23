package com.friendly

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import org.koin.core.module.Module


actual fun decodeBitMapToBase64(bitmap: ImageBitmap): String {
    TODO("Not yet implemented")
}



actual fun decodeBase64ToBitMap(base64: String): ImageBitmap {
    TODO("Not yet implemented")
}

@Composable
actual fun CapturePhoto(onSelect: (ImageBitmap) -> Unit, onClose: ()-> Unit) {
}

actual fun decodeBitMapToByteArray(bitmap: ImageBitmap): ByteArray {
    TODO("Not yet implemented")
}

actual fun cropBitmapToSquare(original: ImageBitmap): ImageBitmap {
    TODO("Not yet implemented")
}

actual fun compressBitmapToDesiredSize(
    original: ImageBitmap,
    maxSizeInKB: Int
): ImageBitmap {
    TODO("Not yet implemented")
}

actual fun resizeImageBitmapWithAspectRatio(
    original: ImageBitmap,
    maxDimension: Int
): ImageBitmap {
    TODO("Not yet implemented")
}

@Composable
actual fun PickPhoto(
    onSelect: (ImageBitmap) -> Unit,
    onClose: () -> Unit
) {
}

@Composable
actual fun SelectLocation(
    onSelect: (Pair<Double, Double>, String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier
) {
}

actual val platformModule: Module
    get() = TODO("Not yet implemented")


@Composable
actual fun ShowStaticMap(
    modifier: Modifier,
    coordinates: Pair<Double, Double>,
    zoom: Float
) {
}

actual fun cropBitmapToPanorama(original: ImageBitmap): ImageBitmap {
    TODO("Not yet implemented")
}