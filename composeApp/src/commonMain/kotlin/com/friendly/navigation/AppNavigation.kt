package com.friendly.navigation

sealed class AppNavigation(val route: String, val label: String){

    data object ChooseSignUpMethod: AppNavigation("chooseSignUpMethod", "Sign Up")
    data object FillUserDetails: AppNavigation(route = "fillUserDetails", label = "Fill User Details")
    data object UploadProfilePicture: AppNavigation(route = "uploadAProfilePicture", label = "Upload A Profile Picture")
    data object RegisterEmailAndPassword: AppNavigation(route = "registerEmailAndPassword", label = "Please provide email and password")

    data object SignIn: AppNavigation("signIn", "Sign In")
    data object CreateEvent : AppNavigation("createEvent", "Create New Event")
    data object UserProfile: AppNavigation(route= "userProfile/{userId}", label = "User Profile")
    data object EventDetails: AppNavigation(route="eventDetails/{eventId}", label = "Event Details")
    data object AppSettings: AppNavigation(route="appSettings", label = "App settings")
    data object HomeScreen: AppNavigation(route="homeScreen", label = "Home Screen")

    data object CreateNewEvent: AppNavigation(route = "createNewEvent", label = "Create New Event")
    data object FillBasicEventDetails: AppNavigation(route="fillBasicEventDetails", label = "Fill Basic EventDetails")
    data object SelectEventDateTime: AppNavigation(route="selectEventDateTime", label = "Select Event Date and Time")
    data object SelectEventLocalization: AppNavigation(route="selectEventLocalization", label = "Select Event Localization")
}