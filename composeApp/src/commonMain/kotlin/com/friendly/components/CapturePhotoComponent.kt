package com.friendly.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun CapturePhoto(onSelect: (ImageBitmap) -> Unit, onClose: ()-> Unit)