package com.friendly.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun StaticMapComponent(modifier: Modifier = Modifier, cameraPositionCoordinates: Pair<Double,Double>, zoom: Float)