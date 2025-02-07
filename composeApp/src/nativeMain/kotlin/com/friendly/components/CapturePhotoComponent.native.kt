package com.friendly.components

import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun CapturePhotoModal(
    onSelect: (ImageBitmap) -> Unit,
    onClose: () -> Unit
) {
}