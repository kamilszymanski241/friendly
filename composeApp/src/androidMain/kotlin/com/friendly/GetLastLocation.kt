package com.friendly

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

fun getLastLocation(
    context: Context,
    onPermissionDenied: () -> Unit,
    onLocationRetrieved: (LatLng) -> Unit
) {
    // Sprawdź, czy aplikacja ma odpowiednie uprawnienia
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        onPermissionDenied()
        return
    }

    // Uzyskaj instancję FusedLocationProviderClient
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Pobierz ostatnią lokalizację
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val newCoordinates = LatLng(location.latitude, location.longitude)
                onLocationRetrieved(newCoordinates)
            } else {
                println("Location is null")
            }
        }
        .addOnFailureListener { exception ->
            println("Failed to get location: ${exception.message}")
        }
}