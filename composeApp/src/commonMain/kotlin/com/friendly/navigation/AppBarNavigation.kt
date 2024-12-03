package com.friendly.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppBarNavigation (val route: String, val label: String, val icon: ImageVector ?= null) {
//    object Discover: AppBarNavigation (route = "discover", label = "Discover", icon = Icons.Default.Search)
//    object UpcomingEvents: AppBarNavigation (route = "upcomingEvents", label = "Upcoming Events", icon = Icons.Default.DateRange)
//    object MyEvents: AppBarNavigation (route = "myEvents", label = "My Events", icon = Icons.Default.AccountBox)
//    object Myvents: AppBarNavigation (route = "myEvents", label = "My Events")
}