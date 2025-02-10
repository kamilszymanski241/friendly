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
actual fun PickPhotoModal(
    onSelect: (ImageBitmap) -> Unit,
    onClose: (String) -> Unit
) {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            try {
                onSelect(uriToImageBitmap(context, uri))
            }catch (e: Exception){
                onClose("Couldn't load the image.")
            }
        } else {
            onClose("Couldn't load the image.")
        }
    }
    LaunchedEffect(Unit) {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}