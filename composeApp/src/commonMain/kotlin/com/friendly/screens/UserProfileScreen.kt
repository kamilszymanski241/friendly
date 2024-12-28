package com.friendly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.components.TopBarWithBackEditAndSettingsButton
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(navController: NavController, viewModel: UserProfileViewModel= koinViewModel ()) {
    Scaffold(
        topBar = {
            TopBarWithBackEditAndSettingsButton(
                navController,
                AppNavigation.HomeScreen,
                AppNavigation.AppSettings
            )
        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        FriendlyAppTheme {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Spacer(modifier = Modifier.size(50.dp))
                AsyncImage(
                    model = viewModel.userDetails.value?.profilePictureUrl,
                    contentDescription = "User Profile Picture",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(180.dp)
                )
                Spacer(modifier = Modifier.size(50.dp))
                Text(
                    text = viewModel.userDetails.value!!.name + " " + viewModel.userDetails.value!!.surname,
                    fontSize = (30.sp),
                    color = Color.White
                )
            }
        }
    }
}