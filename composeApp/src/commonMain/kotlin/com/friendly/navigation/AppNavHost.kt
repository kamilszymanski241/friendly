package com.friendly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.friendly.CapturePhoto
import com.friendly.layouts.MainLayout
import com.friendly.screens.CreateEventScreen
import com.friendly.screens.DiscoverScreen
import com.friendly.screens.EventDetailsScreen
import com.friendly.screens.FillUserDetailsScreen
import com.friendly.screens.MyEventsScreen
import com.friendly.screens.RegisterEmailAndPasswordScreen
import com.friendly.screens.SignInScreen
import com.friendly.screens.SignUpScreen
import com.friendly.screens.UpcomingEventsScreen
import com.friendly.screens.UploadProfilePictureScreen
import com.friendly.screens.UserProfileScreen
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
            DiscoverScreen(navController)
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
        composable(AppNavigation.UserProfile.route) {
            UserProfileScreen(navController)
        }
        composable(
            AppNavigation.EventDetails.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                EventDetailsScreen(eventId, navController)
            } else {
                //TODO()
            }
        }
        composable(AppNavigation.UploadProfilePicture.route) {
            UploadProfilePictureScreen(navController)
        }
        composable(AppNavigation.CapturePhoto.route + "/{callbackKey}") { backStackEntry ->
            val callbackKey = backStackEntry.arguments?.getString("callbackKey")
            CapturePhoto(
                navController = navController,
                onSelect = { base64Image ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(callbackKey ?: "capturedImage", base64Image)
                    navController.popBackStack()
                }
            )
        }
    }
}