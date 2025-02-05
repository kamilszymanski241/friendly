package com.friendly.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.friendly.helpers.uriToImageBitmap


@Composable
actual fun PickPhoto(
    onSelect: (ImageBitmap) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onSelect(uriToImageBitmap(context, uri)!!)
        } else {
            onClose()
        }
    }
    LaunchedEffect(Unit) {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}