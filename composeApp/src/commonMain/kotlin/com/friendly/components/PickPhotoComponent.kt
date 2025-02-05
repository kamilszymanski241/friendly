package com.friendly.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun PickPhoto(onSelect: (ImageBitmap) ->Unit, onClose: ()->Unit)