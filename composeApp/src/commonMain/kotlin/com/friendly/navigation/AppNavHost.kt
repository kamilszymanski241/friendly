package com.friendly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.friendly.screens.CreateEventScreen
import com.friendly.screens.DiscoverScreen
import com.friendly.screens.FillUserDetailsScreen
import com.friendly.screens.MyEventsScreen
import com.friendly.screens.RegisterEmailAndPasswordScreen
import com.friendly.screens.SignInScreen
import com.friendly.screens.SignUpScreen
import com.friendly.screens.UpcomingEventsScreen
import com.friendly.screens.UserProfileScreen
import com.friendly.viewModels.RegisterEmailAndPasswordViewModel
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppNavigation.Discover.route) {
            DiscoverScreen()
        }
        composable(AppNavigation.UpcomingEvents.route) {
            UpcomingEventsScreen()
        }
        composable(AppNavigation.MyEvents.route) {
            MyEventsScreen(navController)
        }
        composable(AppNavigation.CreateEvent.route) {
            CreateEventScreen(navController)
        }
        composable(AppNavigation.SignIn.route) {
            SignInScreen(navController)
        }
        composable(AppNavigation.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(AppNavigation.FillUserDetails.route) {
            FillUserDetailsScreen(navController)
        }
        composable(AppNavigation.RegisterEmailAndPassword.route) {
            RegisterEmailAndPasswordScreen(navController)
        }
        composable(AppNavigation.UserProfile.route){
            UserProfileScreen(navController)
        }
    }
}