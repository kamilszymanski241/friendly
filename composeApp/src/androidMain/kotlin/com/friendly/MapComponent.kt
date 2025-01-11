package com.friendly

import SearchViewModel
import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun MapComponent(onSelect: (Pair<Double,Double>)->Unit, onCancel: ()->Unit) {
    val context = LocalContext.current

    val viewModel = remember { SearchViewModel() }

    val coordinates = remember { mutableStateOf(LatLng(0.0, 0.0)) }
    val address = remember { mutableStateOf("Fetching address...") }

    val markerState = rememberMarkerState(position = coordinates.value)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates.value, 15f)
    }

    getLastLocation(context, onCancel) { newCoordinates ->
        coordinates.value = newCoordinates
        markerState.position = newCoordinates
        cameraPositionState.position = CameraPosition.fromLatLngZoom(newCoordinates, 15f)

        fetchAddress(context, newCoordinates) { fetchedAddress ->
            address.value = fetchedAddress
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(
                compassEnabled = true,
                myLocationButtonEnabled = true
            ),
            onMapClick = { latLng ->
                coordinates.value = latLng
                markerState.position = latLng
                // cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, cameraPositionState.position.zoom)

                fetchAddress(context, latLng) { fetchedAddress ->
                    address.value = fetchedAddress
                }
                println(address.value)
            }
        ) {
            Marker(
                state = markerState,
            )
        }
        SearchComponent(
            viewModel = viewModel,
            onLocationSelected = { selectedPlace ->
                coordinates.value = getLatLngFromPlace(selectedPlace, context)
                println("COORDS:"+ coordinates.value)
                markerState.position = coordinates.value
            })
    }
}

fun getLatLngFromPlace(place: String, context: Context): LatLng {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses = geocoder.getFromLocationName(place, 1)
    return if (addresses!!.isNotEmpty()) {
        val location = addresses[0]
        LatLng(location.latitude, location.longitude)
    } else {
        LatLng(0.0, 0.0)
    }
}

fun fetchAddress(context: Context, latLng: LatLng, onAddressFetched: (String) -> Unit) {
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        if (addresses!!.isNotEmpty()) {
            val address = addresses[0].getAddressLine(0)
            onAddressFetched(address)
        } else {
            onAddressFetched("Address not found")
        }
    } catch (e: Exception) {
        println(e.message)
        onAddressFetched("Error fetching address")
    }
}


