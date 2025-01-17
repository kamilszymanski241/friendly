package com.friendly.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

actual class LocationAndGeocodingHelper(
    private val context: Context
) {
    actual suspend fun getLastLocation(
        onPermissionDenied: () -> Unit,
        onLocationRetrieved: (Pair<Double,Double>) -> Unit
    ) {

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        val newCoordinates = Pair(location.latitude, location.longitude)
                        onLocationRetrieved(newCoordinates)
                    } else {
                        println("Location is null")
                    }
                }
                .addOnFailureListener { exception ->
                    println("Failed to get location: ${exception.message}")
                }
        }catch(e: SecurityException){
            onPermissionDenied()
        }
    }

    actual suspend fun getLatLngFromPlace(
        place: String,
        onLatLngFetched: (Pair<Double,Double>) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= 33) {
            val geocodeListener = Geocoder.GeocodeListener { addresses ->
                if (addresses.isNotEmpty()) {
                    val location = addresses[0]
                    onLatLngFetched(Pair(location.latitude, location.longitude))
                } else {
                    onLatLngFetched(Pair(0.0, 0.0))
                }
            }

            try {
                geocoder.getFromLocationName(place, 1, geocodeListener)
            } catch (e: Exception) {
                println("Error fetching LatLng: ${e.message}")
                onLatLngFetched(Pair(0.0, 0.0))
            }
        } else {
            try {
                val locations = geocoder.getFromLocationName(place, 1)
                if (!locations.isNullOrEmpty()) {
                    val location = locations[0]
                    onLatLngFetched(Pair(location.latitude, location.longitude))
                } else {
                    onLatLngFetched(Pair(0.0, 0.0))
                }
            } catch (e: Exception) {
                println("Error fetching LatLng: ${e.message}")
                onLatLngFetched(Pair(0.0, 0.0))
            }
        }
    }

    actual suspend fun fetchAddress(
        latLng: Pair<Double, Double>,
        onAddressFetched: (String) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                geocoder.getFromLocation(
                    latLng.first,
                    latLng.second,
                    1,
                    object : Geocoder.GeocodeListener {
                        override fun onGeocode(addresses: MutableList<Address>) {
                            if (addresses.isNotEmpty()) {
                                val address = addresses[0].getAddressLine(0)
                                onAddressFetched(address)
                            } else {
                                onAddressFetched("Address not found")
                            }
                        }

                        override fun onError(errorMessage: String?) {
                            println("Error fetching address: $errorMessage")
                            onAddressFetched("Error fetching address")
                        }
                    }
                )
            } else {
                val addresses = geocoder.getFromLocation(latLng.first, latLng.second, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0].getAddressLine(0)
                    onAddressFetched(address)
                } else {
                    onAddressFetched("Address not found")
                }
            }
        } catch (e: Exception) {
            println("Exception fetching address: ${e.message}")
            onAddressFetched("Error fetching address")
        }
    }
}