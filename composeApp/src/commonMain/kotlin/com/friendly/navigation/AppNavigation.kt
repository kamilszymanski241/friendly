package com.friendly.navigation

sealed class AppNavigation(val route: String){
    object CreateEvent : AppNavigation("createEvent")
}