package com.friendly.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppNavigation(val route: String, val label: String){
    object CreateEvent : AppNavigation("createEvent", "Create New Event")
    object SignIn: AppNavigation("signIn", "Sign In")
    object SignUp: AppNavigation("signUp", "Sign Up")
    object Discover: AppBarNavigation (route = "discover", label = "Discover")
    object UpcomingEvents: AppBarNavigation (route = "upcomingEvents", label = "Upcoming Events")
    object MyEvents: AppBarNavigation (route = "myEvents", label = "My Events")
}