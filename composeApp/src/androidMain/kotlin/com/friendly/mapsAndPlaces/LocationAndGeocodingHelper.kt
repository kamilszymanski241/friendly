package com.friendly.mapsAndPlaces

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Build
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

object LocationAndGeocodingHelper {
    fun getLastLocation(
        context: Context,
        onPermissionDenied: () -> Unit,
        onLocationRetrieved: (LatLng) -> Unit
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            onPermissionDenied()
            return
        }

        val fusedLocationClient: FusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)

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

    fun getLatLngFromPlace(
        place: String,
        context: Context,
        onLatLngFetched: (LatLng) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())

        if (Build.VERSION.SDK_INT >= 33) {
            val geocodeListener = Geocoder.GeocodeListener { addresses ->
                if (addresses.isNotEmpty()) {
                    val location = addresses[0]
                    onLatLngFetched(LatLng(location.latitude, location.longitude))
                } else {
                    onLatLngFetched(LatLng(0.0, 0.0))
                }
            }

            try {
                geocoder.getFromLocationName(place, 1, geocodeListener)
            } catch (e: Exception) {
                println("Error fetching LatLng: ${e.message}")
                onLatLngFetched(LatLng(0.0, 0.0))
            }
        } else {
            try {
                val locations = geocoder.getFromLocationName(place, 1)
                if (!locations.isNullOrEmpty()) {
                    val location = locations[0]
                    onLatLngFetched(LatLng(location.latitude, location.longitude))
                } else {
                    onLatLngFetched(LatLng(0.0, 0.0))
                }
            } catch (e: Exception) {
                println("Error fetching LatLng: ${e.message}")
                onLatLngFetched(LatLng(0.0, 0.0))
            }
        }
    }



    fun fetchAddress(
        context: Context,
        latLng: LatLng,
        onAddressFetched: (String) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT >= 33) {
                geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
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
                val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
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