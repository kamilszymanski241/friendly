package com.friendly.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(navController: NavController, viewModel: UserProfileViewModel= koinViewModel ())
{
    FriendlyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = viewModel.userDetails.value!!.name
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = viewModel.userDetails.value!!.surname
            )
            TextButton(
                onClick = {
                    viewModel.onSignOut()
                    navController.navigate(AppNavigation.Discover.route)
                }
            ) {
                Text(
                    text = "Sign Out",
                    color = Color.Red
                )
            }
        }
    }
}