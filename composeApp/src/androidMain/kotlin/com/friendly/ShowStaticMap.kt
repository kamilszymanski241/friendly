package com.friendly

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.friendly.mapsAndPlaces.components.StaticMapComponent
import com.google.android.gms.maps.model.LatLng

@Composable
actual fun ShowStaticMap(modifier: Modifier, coordinates: Pair<Double,Double>, zoom: Float) {
    StaticMapComponent(modifier, LatLng(coordinates.first, coordinates.second), zoom)
}