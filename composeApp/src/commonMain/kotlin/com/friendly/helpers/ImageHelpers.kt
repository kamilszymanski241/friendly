package com.friendly.helpers

import androidx.compose.ui.graphics.ImageBitmap

expect fun resizeImageBitmapWithAspectRatio(original: ImageBitmap, maxDimension: Int): ImageBitmap

expect fun cropBitmapToPanorama(original: ImageBitmap): ImageBitmap

expect fun cropBitmapToSquare(original: ImageBitmap): ImageBitmap

expect fun decodeBitMapToByteArray(bitmap: ImageBitmap): ByteArray