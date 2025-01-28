package com.friendly.screens.userProfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.userProfile.AppSettingsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppSettingsScreen(navController: NavController, viewModel: AppSettingsScreenViewModel = koinViewModel ()) {
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Settings") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Button(
                    onClick = {
                        viewModel.onSignOut()
                        navController.navigate(AppNavigation.HomeScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.Red
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text(
                        text = "Sign Out",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}