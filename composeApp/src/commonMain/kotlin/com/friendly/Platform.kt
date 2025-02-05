package com.friendly

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import org.koin.core.module.Module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform