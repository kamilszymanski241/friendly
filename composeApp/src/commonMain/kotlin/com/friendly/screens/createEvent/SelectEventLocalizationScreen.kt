package com.friendly.screens.createEvent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.friendly.MapComponent
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.createEvent.SelectEventLocalizationScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectEventLocalizationScreen(navController: NavController, viewModel: SelectEventLocalizationScreenViewModel  = koinViewModel ()) {
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Location") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                MapComponent(onSelect = {viewModel.setLocalization(it)}, onCancel = {})
                Button(
                    onClick = {
                        navController.navigate(AppNavigation.HomeScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Continue")
                }
            }
        }
    }
}