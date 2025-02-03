package com.friendly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.friendly.screens.userProfile.AppSettingsScreen
import com.friendly.screens.eventDetails.EventDetailsScreen
import com.friendly.screens.userProfile.UserProfileScreen
import com.friendly.screens.createEvent.FillTitleAndDescriptionScreen
import com.friendly.screens.createEvent.SelectDateAndTimeScreen
import com.friendly.screens.createEvent.SelectLocationScreen
import com.friendly.screens.eventDetails.EditEventDetailsScreen
import com.friendly.screens.eventDetails.EventSettingsScreen
import com.friendly.screens.eventDetails.ShowAllParticipantsScreen
import com.friendly.screens.home.HomeScreen
import com.friendly.screens.signInSignUp.ChooseSignUpMethodScreen
import com.friendly.screens.signInSignUp.FillUserDetailsScreen
import com.friendly.screens.signInSignUp.RegisterEmailAndPasswordScreen
import com.friendly.screens.signInSignUp.SignInScreen
import com.friendly.screens.signInSignUp.UploadProfilePictureScreen
import com.friendly.screens.userProfile.EditUserDetailsScreen
import com.friendly.viewModels.createEvent.CreateEventViewModel
import com.friendly.viewModels.signInSignUp.SignUpViewModel

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
            ChooseSignUpMethodScreen(navController)
        }
        composable(AppNavigation.EditUserDetails.route){
            EditUserDetailsScreen(navController)
        }
        composable(AppNavigation.AppSettings.route) {
            AppSettingsScreen(navController)
        }
        composable(
            AppNavigation.EditEventDetails.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType }))
        { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                EditEventDetailsScreen(eventId = eventId, navController = navController)
            }
        }
        composable(AppNavigation.EventSettings.route) {
            EventSettingsScreen(navController)
        }
        composable(
            AppNavigation.UserProfile.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            if (userId != null) {
                UserProfileScreen(userId = userId, navController = navController)
            }
        }
        composable(
            AppNavigation.EventDetails.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                EventDetailsScreen(eventId = eventId, navController = navController)
            }
        }
        composable(
            AppNavigation.ShowAllParticipants.route,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            if (eventId != null) {
                ShowAllParticipantsScreen(eventId = eventId, navController = navController)
            }
        }
        navigation(
            startDestination = AppNavigation.FillEventTitleAndDescription.route,
            route = AppNavigation.CreateEvent.route
        ){
            composable(AppNavigation.FillEventTitleAndDescription.route) {
                val viewModel = it.sharedViewModel<CreateEventViewModel>(navController)
                FillTitleAndDescriptionScreen(navController, viewModel)
            }
            composable(AppNavigation.SelectEventDateAndTime.route) {
                val viewModel = it.sharedViewModel<CreateEventViewModel>(navController)
                SelectDateAndTimeScreen(navController, viewModel)
            }
            composable(AppNavigation.SelectEventLocation.route) {
                val viewModel = it.sharedViewModel<CreateEventViewModel>(navController)
                SelectLocationScreen(navController, viewModel)
            }
        }
        navigation(
            startDestination = AppNavigation.ChooseSignUpMethod.route,
            route = AppNavigation.SignUp.route
        ){
            composable(AppNavigation.ChooseSignUpMethod.route) {
                ChooseSignUpMethodScreen(navController)
            }
            composable(AppNavigation.FillUserDetails.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navController)
                FillUserDetailsScreen(navController, viewModel)
            }
            composable(AppNavigation.UploadProfilePicture.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navController)
                UploadProfilePictureScreen(navController, viewModel)
            }
            composable(AppNavigation.RegisterEmailAndPassword.route) {
                val viewModel = it.sharedViewModel<SignUpViewModel>(navController)
                RegisterEmailAndPasswordScreen(navController, viewModel)
            }
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry  = remember(this){
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}
