package com.friendly.navigation

sealed class AppNavigation(val route: String, val label: String){
    object CreateEvent : AppNavigation("createEvent", "Create New Event")
    object SignIn: AppNavigation("signIn", "Sign In")
    object SignUp: AppNavigation("signUp", "Sign Up")
    object Discover: AppNavigation (route = "discover", label = "Discover")
    object UpcomingEvents: AppNavigation (route = "upcomingEvents", label = "Upcoming Events")
    object MyEvents: AppNavigation (route = "myEvents", label = "My Events")
    object FillUserDetails: AppNavigation(route = "fillUserDetails", label = "Fill User Details")
    object UploadProfilePicture: AppNavigation(route = "uploadAProfilePicture", label = "Upload A Profile Picture")
}