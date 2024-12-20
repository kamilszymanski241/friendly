package com.friendly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.friendly.screens.home.UpcomingEventsScreen
import com.friendly.screens.home.DiscoverScreen
import com.friendly.screens.home.MyEventsScreen

@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    appNavController: NavController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppNavigation.Discover.route) {
            DiscoverScreen(navController, appNavController)
        }
        composable(AppNavigation.UpcomingEvents.route) {
            UpcomingEventsScreen(navController)
        }
        composable(AppNavigation.MyEvents.route) {
            MyEventsScreen(navController)
        }
    }
}