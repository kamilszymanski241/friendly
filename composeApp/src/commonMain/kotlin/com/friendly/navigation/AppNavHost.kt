package com.friendly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.friendly.screens.AppSettingsScreen
import com.friendly.screens.EventDetailsScreen
import com.friendly.screens.signInSignUp.SignInScreen
import com.friendly.screens.UserProfileScreen
import com.friendly.screens.createEvent.FillBasicEventDetailsScreen
import com.friendly.screens.createEvent.SelectDateTimeScreen
import com.friendly.screens.createEvent.SelectEventLocalizationScreen
import com.friendly.screens.home.HomeScreen
import com.friendly.screens.signInSignUp.ChooseSignUpMethod
import com.friendly.screens.signInSignUp.FillUserDetailsScreen
import com.friendly.screens.signInSignUp.RegisterEmailAndPasswordScreen
import com.friendly.screens.signInSignUp.UploadProfilePictureScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppNavigation.HomeScreen.route)
        {
            HomeScreen(navController)
        }
        composable(AppNavigation.SignIn.route) {
            SignInScreen(navController)
        }
        composable(AppNavigation.ChooseSignUpMethod.route) {
            ChooseSignUpMethod(navController)
        }
        composable(AppNavigation.FillUserDetails.route) {
            FillUserDetailsScreen(navController)
        }
        composable(AppNavigation.UploadProfilePicture.route) {
            UploadProfilePictureScreen(navController)
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
                EventDetailsScreen(eventId = eventId, navController = navController)
            } else {
                //TODO()
            }
        }
        navigation(
            startDestination = AppNavigation.FillBasicEventDetails.route,
            route = AppNavigation.CreateEvent.route
        ){
            composable(AppNavigation.FillBasicEventDetails.route) {
                FillBasicEventDetailsScreen(navController)
            }
            composable(AppNavigation.SelectEventDateTime.route) {
                SelectDateTimeScreen(navController)
            }
            composable(AppNavigation.SelectEventLocalization.route) {
                SelectEventLocalizationScreen(navController)
            }
        }
        composable(AppNavigation.AppSettings.route) {
            AppSettingsScreen(navController)
        }
    }
}
