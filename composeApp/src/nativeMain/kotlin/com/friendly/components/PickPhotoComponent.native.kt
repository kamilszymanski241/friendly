package com.friendly.components

import androidx.compose.ui.graphics.ImageBitmap

@Composable
actual fun PickPhoto(
    onSelect: (ImageBitmap) -> Unit,
    onClose: () -> Unit
) {
}