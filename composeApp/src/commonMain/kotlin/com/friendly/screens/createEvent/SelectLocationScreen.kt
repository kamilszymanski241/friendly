package com.friendly.screens.createEvent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.SearchLocationComponent
import com.friendly.components.StaticMapComponent
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.createEvent.CreateEventViewModel

@Composable
fun SelectLocationScreen(navController: NavController, viewModel: CreateEventViewModel) {

    val locationText = viewModel.selectedLocationAddress.collectAsState("")

    val coordinates = viewModel.selectedLocationCoordinates.collectAsState()

    LaunchedEffect(Unit)
    {
        viewModel.setInitialLocation()
    }

    LaunchedEffect(coordinates.value){
        viewModel.updateAddressOnCoordinatesChange()
    }

    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Location") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .fillMaxSize()
            )
            {
                SearchLocationComponent(
                    onLocationSelected = {locationText ->
                        viewModel.onLocationTextChange(locationText)},
                    modifier = Modifier.fillMaxSize(),
                    leadingIcon = Icons.Default.Search,
                    textFieldPlaceHolder = "Search for a place")
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
                                text = locationText.value,
                                fontSize = 15.sp,
                                color = Color.Black,
                                maxLines = 2,
                                modifier = Modifier.padding(4.dp),
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {
                        viewModel.onConfirm(
                            onSuccess = {
                                navController.navigate(AppNavigation.HomeScreen.route){
                                    popUpTo(AppNavigation.CreateEvent.route) { inclusive = true }
                                }
                            },
                            onFailure = {}//TODO())
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Create event!")
                }
            }
        }
    }
}