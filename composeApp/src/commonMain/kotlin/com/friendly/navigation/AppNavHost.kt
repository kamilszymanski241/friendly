package com.friendly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.TopBarType
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
import org.koin.compose.koinInject

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    layoutManager: ILayoutManager = koinInject ()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppNavigation.Discover.route) {
            layoutManager.setTopBar(TopBarType.Main)
            layoutManager.setBottomBar(BottomBarType.MainNavigation)
            DiscoverScreen(navController)
        }
        composable(AppNavigation.UpcomingEvents.route) {
            layoutManager.setTopBar(TopBarType.Main)
            layoutManager.setBottomBar(BottomBarType.MainNavigation)
            UpcomingEventsScreen()
        }
        composable(AppNavigation.MyEvents.route) {
            layoutManager.setTopBar(TopBarType.Main)
            layoutManager.setBottomBar(BottomBarType.MainNavigation)
            MyEventsScreen(navController)
        }
        composable(AppNavigation.SignIn.route) {
            layoutManager.setTopBar(TopBarType.WithBackButton)
            layoutManager.setBottomBar(BottomBarType.Empty)
            SignInScreen(navController)
        }
        composable(AppNavigation.SignUp.route) {
            layoutManager.setTopBar(TopBarType.WithBackButton)
            layoutManager.setBottomBar(BottomBarType.Empty)
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
        composable(AppNavigation.CreateEvent.route) {
            CreateEventScreen(navController)
        }
    }
}