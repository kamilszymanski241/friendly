package com.friendly.navigation

sealed class AppNavigation(val route: String){
    object CreateEvent : AppNavigation("createEvent")
    object SignIn: AppNavigation("signIn")
    object SignUp: AppNavigation("signUp")
}