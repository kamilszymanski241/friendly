package com.friendly

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.util.Base64
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
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
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import java.io.ByteArrayOutputStream


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual val nativeModule = module{
    single<IPlacesClientProvider>{PlacesClientProvider()}
}
actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)
    engine {
        config {
            retryOnConnectionFailure(true)
        }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            serializersModule
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = 30_000
        connectTimeoutMillis = 30_000
        socketTimeoutMillis = 30_000
    }
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}
actual fun decodeBitMapToBase64(bitmap: ImageBitmap): String {
    val androidBitmap = bitmap.asAndroidBitmap()
    val outputStream = ByteArrayOutputStream()
    androidBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

actual fun decodeBase64ToBitMap(base64: String): ImageBitmap {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    return bitmap.asImageBitmap()
}

actual fun decodeByteArrayToBitMap(byteArray: ByteArray): ImageBitmap?{
    return try{
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size).asImageBitmap()
    }catch(e: Exception)
    {
        null
    }
}

actual fun decodeBitMapToByteArray(bitmap: ImageBitmap): ByteArray {
    return try {
        val androidBitmap = bitmap.asAndroidBitmap()
        val outputStream = ByteArrayOutputStream()
        androidBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.toByteArray()
    } catch (e: Exception) {
        throw e
    }
}

actual fun cropBitmapToSquare(original: ImageBitmap): ImageBitmap {
    val width = original.width
    val height = original.height
    val size = minOf(width, height)
    val x = (width - size) / 2
    val y = (height - size) / 2
    val new = Bitmap.createBitmap(original.asAndroidBitmap(), x, y, size, size)
    return new.asImageBitmap()
}

actual fun compressBitmapToDesiredSize(original: ImageBitmap, maxSizeInKB: Int): ImageBitmap {
    val bitmap = original.asAndroidBitmap()
    var quality = 100
    var compressedBitmap: Bitmap? = null

    do {
        println(quality)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        val compressedData = outputStream.toByteArray()

        if (compressedData.size <= (maxSizeInKB*1024)) {
            compressedBitmap = BitmapFactory.decodeByteArray(compressedData, 0, compressedData.size)
            break
        }

        quality -= 5
    } while (quality > 0)

    return compressedBitmap?.asImageBitmap() ?: original
}

actual fun resizeImageBitmapWithAspectRatio(original: ImageBitmap, maxDimension: Int): ImageBitmap {
    val bitmap = original.asAndroidBitmap()
    val width = bitmap.width
    val height = bitmap.height

    val scale = if (width > height) maxDimension / width.toFloat() else maxDimension / height.toFloat()
    val targetWidth = (width * scale).toInt()
    val targetHeight = (height * scale).toInt()

    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, true)
    return scaledBitmap.asImageBitmap()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun CapturePhoto(onSelect: (ImageBitmap) -> Unit, onClose: () -> Unit) {
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
            // Permission granted
            println("PERMISSION GRANTED")
        } else {
            // Handle permission denial
            println("PERMISSION DENIED")
            onClose()
            //TODO()
        }
    }

    LaunchedEffect(cameraPermissionState) {
        if (!cameraPermissionState.status.isGranted && cameraPermissionState.status.shouldShowRationale) {
            // Show rationale- alert dialog
            showRationale.value = true
        } else {
            // Request permission
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
                        onClick = { onSelect(cropBitmapToSquare(capturedBitmap.value!!)) },
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

@Composable
actual fun PickPhoto(
    onSelect: (ImageBitmap) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            onSelect(cropBitmapToSquare(uriToImageBitmap(context, uri)!!))
        } else {
            onClose()
        }
    }
    LaunchedEffect(Unit) {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}

fun uriToImageBitmap(context: Context, uri: Uri): ImageBitmap? {
    return try {
        val resolver = context.contentResolver
        val inputStream = resolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        bitmap?.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}