package com.friendly.screens.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.friendly.layouts.bars.HomeScreenNavBar
import com.friendly.layouts.bars.HomeScreenTopBar
import com.friendly.navigation.AppNavigation
import com.friendly.navigation.HomeNavHost
import com.friendly.themes.FriendlyAppTheme

@Composable
fun HomeScreen(appNavController: NavController) {
    val homeNavController = rememberNavController()
    FriendlyAppTheme {
        Scaffold (
            topBar = { HomeScreenTopBar(appNavController) },
            bottomBar = { HomeScreenNavBar(homeNavController) },
            containerColor = MaterialTheme.colorScheme.secondary
        ){innerPadding->
            HomeNavHost(
                modifier = Modifier.padding(innerPadding),
                navController = homeNavController,
                appNavController = appNavController,
                startDestination = AppNavigation.Discover.route
            )
            //content(innerPadding)
        }
    }
}