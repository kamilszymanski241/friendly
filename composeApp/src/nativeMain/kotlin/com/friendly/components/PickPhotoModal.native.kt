package com.friendly.components

import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun PickPhotoModal(
    onSelect: (ImageBitmap) -> Unit,
    onClose: (String) -> Unit
) {
}