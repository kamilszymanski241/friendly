package com.friendly.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class ScaffoldViewState(
    val topBar: @Composable() ()->Unit,
    val bottomBar: @Composable() ()->Unit
)