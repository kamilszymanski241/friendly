package com.friendly.screens.eventDetails

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.userProfile.AppSettingsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EventSettingsScreen(navController: NavController, viewModel: AppSettingsScreenViewModel = koinViewModel ()) {
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Event Settings") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
        }
    }
}