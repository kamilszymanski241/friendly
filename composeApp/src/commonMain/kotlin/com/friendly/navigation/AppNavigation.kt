package com.friendly.navigation

sealed class AppNavigation(val route: String, val label: String){
    data object Discover: AppNavigation (route = "discover", label = "Discover")
    data object UpcomingEvents: AppNavigation (route = "upcomingEvents", label = "Upcoming Events")
    data object MyEvents: AppNavigation (route = "myEvents", label = "My Events")

    data object ChooseSignUpMethod: AppNavigation("chooseSignUpMethod", "Sign Up")
    data object FillUserDetails: AppNavigation(route = "fillUserDetails", label = "Fill User Details")
    data object UploadProfilePicture: AppNavigation(route = "uploadAProfilePicture", label = "Upload A Profile Picture")
    data object RegisterEmailAndPassword: AppNavigation(route = "registerEmailAndPassword", label = "Please provide email and password")

    data object SignIn: AppNavigation("signIn", "Sign In")
    data object CreateEvent : AppNavigation("createEvent", "Create New Event")
    data object UserProfile: AppNavigation(route= "userProfile", label = "User Profile")
    data object EventDetails: AppNavigation(route="eventDetails/{eventId}", label = "Event Details")
    data object AppSettings: AppNavigation(route="appSetings", label = "App settings")
    data object HomeScreen: AppNavigation(route="homeScreen", label = "Home Screen")
}