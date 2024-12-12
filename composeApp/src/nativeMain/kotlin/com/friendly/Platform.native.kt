package com.friendly

import androidx.compose.ui.graphics.ImageBitmap
import androidx.navigation.NavController


actual fun convertBitMapToBase64(bitmap: ImageBitmap): String {
    TODO("Not yet implemented")
}


@Composable
actual fun CapturePhoto(
    navController: NavController,
    onSelect: (String) -> Unit
) {
}

actual fun convertBase64ToBitMap(base64: String): ImageBitmap {
    TODO("Not yet implemented")
}