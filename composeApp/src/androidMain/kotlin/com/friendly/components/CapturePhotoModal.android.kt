package com.friendly.components

import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.friendly.helpers.rotate
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun CapturePhotoModal(onSelect: (ImageBitmap) -> Unit, onClose: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val capturedBitmap = remember { mutableStateOf<ImageBitmap?>(null) }
    val lensFacing = rememberSaveable { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
    val cameraProvider = remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val showRationale = remember { mutableStateOf(false) }

    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    BackHandler {
        if (capturedBitmap.value != null) {
            capturedBitmap.value = null
        } else {
            onClose()
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            println("PERMISSION GRANTED")
        } else {
            println("PERMISSION DENIED")
            onClose()
            //TODO()
        }
    }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
            showRationale.value = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }


    fun bindCameraUseCases() {
        cameraProvider.value?.let { provider ->
            try {
                provider.unbindAll()

                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(lensFacing.value)
                    .build()

                provider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                println("Error binding camera use cases: ${e.message}")
            }
        }
    }

    fun getRotationDegrees(): Float {
        return if (lensFacing.value == CameraSelector.LENS_FACING_FRONT) {
            270f
        } else {
            90f
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        if(showRationale.value)
        {
            AlertDialog(
                onDismissRequest = { showRationale.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        showRationale.value = false
                    }) {
                        Text(
                            "Ok",
                            color = Color.Black
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { onClose() }
                    )
                    {
                        Text(
                            "Cancel",
                            color = Color.Black)
                    }
                },
                text = { Text("You need to allow the needed permissions") }
            )
        }
        if (capturedBitmap.value == null) {
            AndroidView(
                factory = { previewView },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                update = {
                    cameraProviderFuture.addListener({
                        try {
                            val provider = cameraProviderFuture.get()
                            cameraProvider.value = provider
                            bindCameraUseCases()
                        } catch (e: Exception) {
                            println("Error initializing camera: ${e.message}")
                        }
                    }, ContextCompat.getMainExecutor(context))
                }
            )
            IconButton(
                onClick = {
                    lensFacing.value = if (lensFacing.value == CameraSelector.LENS_FACING_BACK) {
                        CameraSelector.LENS_FACING_FRONT
                    } else {
                        CameraSelector.LENS_FACING_BACK
                    }
                    bindCameraUseCases()
                },
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Switch Camera"
                )
            }
            IconButton(
                onClick = { onClose() },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
            Button(
                onClick = {
                    imageCapture.takePicture(
                        ContextCompat.getMainExecutor(context),
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(image: ImageProxy) {
                                val rotationDegrees = getRotationDegrees()
                                val bitmap = image.toBitmap().rotate(rotationDegrees)
                                image.close()
                                capturedBitmap.value = bitmap.asImageBitmap()
                            }

                            override fun onError(exception: ImageCaptureException) {
                                println("Error capturing photo: ${exception.message}")
                            }
                        }
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .size(80.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .background(Color.Black)
                ) {
                    Image(
                        bitmap = capturedBitmap.value!!,
                        contentDescription = "Captured Photo",
                        modifier = Modifier
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )
                }
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { capturedBitmap.value = null },
                        modifier = Modifier
                            .size(80.dp)
                            .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                        )
                    }
                    IconButton(
                        onClick = { onSelect(capturedBitmap.value!!) },
                        modifier = Modifier
                            .size(80.dp)
                            .background(MaterialTheme.colorScheme.tertiary, shape = CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                        )
                    }
                }
            }
        }
    }
}