package com.friendly.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
actual fun StaticMapComponent(modifier: Modifier, cameraPositionCoordinates: Pair<Double,Double>, zoom: Float){

    val markerState = rememberMarkerState(position = LatLng(cameraPositionCoordinates.first, cameraPositionCoordinates.second))

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(cameraPositionCoordinates.first, cameraPositionCoordinates.second), zoom)
    }

    LaunchedEffect(cameraPositionCoordinates) {
        markerState.position = LatLng(cameraPositionCoordinates.first, cameraPositionCoordinates.second)
        cameraPositionState.position =
            CameraPosition.fromLatLngZoom(LatLng(cameraPositionCoordinates.first, cameraPositionCoordinates.second), zoom)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            myLocationButtonEnabled = false,
            zoomControlsEnabled = false,
            scrollGesturesEnabled = false,
            zoomGesturesEnabled = false,
            tiltGesturesEnabled = false,
            rotationGesturesEnabled = false
        )
    ) {
        Marker(
            state = markerState,
        )
    }
}