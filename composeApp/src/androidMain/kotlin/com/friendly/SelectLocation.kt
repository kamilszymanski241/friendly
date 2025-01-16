package com.friendly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.friendly.mapsAndPlaces.LocationAndGeocodingHelper
import com.friendly.mapsAndPlaces.components.SearchLocationComponent
import com.friendly.mapsAndPlaces.components.StaticMapComponent
import com.google.android.gms.maps.model.LatLng

@Composable
actual fun SelectLocation(
    onSelect: (Pair<Double, Double>, String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current

    val coordinates = remember { mutableStateOf((LatLng(0.0, 0.0))) }

    val address = remember { mutableStateOf("Fetching address...") }

    LaunchedEffect(Unit) {
        LocationAndGeocodingHelper.getLastLocation(context, onCancel) { newCoordinates ->
            coordinates.value = newCoordinates
        }
    }

    LaunchedEffect(coordinates.value){
        LocationAndGeocodingHelper.fetchAddress(context, coordinates.value) { fetchedAddress ->
            address.value = fetchedAddress
            onSelect(Pair(coordinates.value.latitude,coordinates.value.longitude), address.value)
        }
    }

    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .offset(y = 65.dp)
        ) {
            StaticMapComponent(
                modifier=Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp)),
                coordinates.value, 15f)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        Icons.Default.Place,
                        "",
                        modifier = Modifier.size(40.dp).padding(4.dp),
                        Color.Black
                    )
                }
                Column {
                    Text(
                        "Selected address:",
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(4.dp),
                    )
                    Text(
                        text = address.value,
                        fontSize = 15.sp,
                        color = Color.Black,
                        maxLines = 2,
                        modifier = Modifier.padding(4.dp),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        SearchLocationComponent(
            modifier = Modifier.align(Alignment.TopCenter),
            onLocationSelected = { selectedPlace ->
                LocationAndGeocodingHelper.getLatLngFromPlace(selectedPlace, context, onLatLngFetched = {coordinates.value = it})
                LocationAndGeocodingHelper.fetchAddress(
                    context,
                    coordinates.value,
                    onAddressFetched = { address.value = it })
            })
    }
}



