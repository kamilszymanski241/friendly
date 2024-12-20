package com.friendly.navigation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.friendly.layouts.bars.HomeScreenNavBar
import com.friendly.layouts.bars.HomeScreenTopBar
import com.friendly.screens.AppSettingsScreen
import com.friendly.screens.EventDetailsScreen
import com.friendly.screens.SignInScreen
import com.friendly.screens.UserProfileScreen
import com.friendly.screens.home.CreateEventScreen
import com.friendly.screens.home.DiscoverScreen
import com.friendly.screens.home.HomeScreen
import com.friendly.screens.home.MyEventsScreen
import com.friendly.screens.home.UpcomingEventsScreen
import com.friendly.screens.signUp.ChooseSignUpMethod
import com.friendly.screens.signUp.FillUserDetailsScreen
import com.friendly.screens.signUp.RegisterEmailAndPasswordScreen
import com.friendly.screens.signUp.UploadProfilePictureScreen

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
/*        composable(AppNavigation.Discover.route) {
            DiscoverScreen(navController)
        }
        composable(AppNavigation.UpcomingEvents.route) {
            UpcomingEventsScreen(navController)
        }
        composable(AppNavigation.MyEvents.route) {
            MyEventsScreen(navController)*/
        //}
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
        composable(AppNavigation.CreateEvent.route) {
            CreateEventScreen(navController)
        }
        composable(AppNavigation.AppSettings.route) {
            AppSettingsScreen(navController)
        }
    }
}
/*
fun NavGraphBuilder.HomeScreenNav(
    navController: NavHostController
){
    navigation(startDestination = AppNavigation.Discover.route, route = AppNavigation.HomeScreen.route) {
        composable(AppNavigation.Discover.route) {
            HomeScreen(navController){ DiscoverScreen(navController) }
        }
        composable(AppNavigation.UpcomingEvents.route) {
            UpcomingEventsScreen()
        }
        composable(AppNavigation.MyEvents.route) {
            MyEventsScreen(navController)
        }
    }
}*/
