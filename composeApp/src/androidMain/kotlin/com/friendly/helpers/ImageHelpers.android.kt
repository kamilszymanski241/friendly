package com.friendly.helpers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

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

actual fun cropBitmapToPanorama(original: ImageBitmap): ImageBitmap {
    val width = original.width
    val height = original.height

    if (width.toFloat() / height == 16f / 9f) {
        return original
    }

    val targetWidth: Int
    val targetHeight: Int

    if (width.toFloat() / height > 16f / 9f) {
        targetHeight = height
        targetWidth = (height * 16f / 9f).toInt()
    } else {
        targetWidth = width
        targetHeight = (width * 9f / 16f).toInt()
    }

    val x = (width - targetWidth) / 2
    val y = (height - targetHeight) / 2

    val new = Bitmap.createBitmap(original.asAndroidBitmap(), x, y, targetWidth, targetHeight)
    return new.asImageBitmap()
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

fun uriToImageBitmap(context: Context, uri: Uri): ImageBitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw Exception("Couldn't load image: $uri")

    inputStream.use { stream ->
        val bitmap = BitmapFactory.decodeStream(stream)
            ?: throw Exception("Couldn't decode bitmap for: $uri")

        return bitmap.asImageBitmap()
    }
}

fun Bitmap.rotate(degrees: Float): Bitmap {
    val matrix = Matrix().apply { postRotate(degrees) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}