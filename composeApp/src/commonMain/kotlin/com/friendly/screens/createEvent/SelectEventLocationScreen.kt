package com.friendly.screens.createEvent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.SelectLocation
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.createEvent.CreateEventViewModel
@Composable
fun SelectEventLocationScreen(navController: NavController, viewModel: CreateEventViewModel) {
    val location = viewModel.selectedLocationText.collectAsState("")
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
                SelectLocation(
                    onSelect = {location, locationText ->
                        viewModel.onLocationChange(location)
                        viewModel.onLocationTextChange(locationText)},
                    onCancel = {},
                    modifier = Modifier.align(Alignment.TopCenter).fillMaxSize())
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