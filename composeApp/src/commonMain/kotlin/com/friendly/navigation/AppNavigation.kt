package com.friendly.navigation

sealed class AppNavigation(val route: String, val label: String){

    data object SignUp: AppNavigation(route = "signUp", label = "Sign Up")
    data object ChooseSignUpMethod: AppNavigation("chooseSignUpMethod", "Sign Up")
    data object FillUserDetails: AppNavigation(route = "fillUserDetails", label = "Fill User Details")
    data object UploadProfilePicture: AppNavigation(route = "uploadAProfilePicture", label = "Upload A Profile Picture")
    data object RegisterEmailAndPassword: AppNavigation(route = "registerEmailAndPassword", label = "Please provide email and password")
    data object SignIn: AppNavigation("signIn", "Sign In")

    data object UserProfile: AppNavigation(route= "userProfile/{userId}", label = "User Profile")
    data object EditUserDetails: AppNavigation(route = "editUserDetails", label = "Edit User Details")
    data object AppSettings: AppNavigation(route="appSettings", label = "App settings")

    data object EventDetails: AppNavigation(route="eventDetails/{eventId}", label = "Event Details")
    data object HomeScreen: AppNavigation(route="homeScreen", label = "Home Screen")

    data object CreateEvent : AppNavigation("createEvent", "Create New Event")
    data object FillEventTitleAndDescription: AppNavigation(route="fillEventTitleAndDescription", label = "Fill Basic EventDetails")
    data object SelectEventDateAndTime: AppNavigation(route="selectEventDateAndTime", label = "Select Event Date and Time")
    data object SelectEventLocation: AppNavigation(route="selectEventLocation", label = "Select Event Localization")
}