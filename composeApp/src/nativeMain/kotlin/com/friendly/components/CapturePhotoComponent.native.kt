package com.friendly.components

import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun CapturePhoto(
    onSelect: (ImageBitmap) -> Unit,
    onClose: () -> Unit
) {
}