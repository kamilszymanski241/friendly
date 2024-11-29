package com.friendly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.friendly.components.NavBar
import com.friendly.components.TopBar
import com.friendly.di.initKoin
import com.friendly.features.CreateEventScreen
import com.friendly.features.DiscoverScreen
import com.friendly.features.MyEventsScreen
import com.friendly.features.UpcomingEventsScreen
import com.friendly.navigation.AppBarNavigation
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    initKoin()
        FriendlyAppTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { TopBar() },
                    bottomBar = {NavBar(navController)},
                    containerColor = MaterialTheme.colorScheme.secondary
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppBarNavigation.Discover.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(AppBarNavigation.Discover.route){
                            DiscoverScreen()
                        }
                        composable(AppBarNavigation.UpcomingEvents.route){
                            UpcomingEventsScreen()
                        }
                        composable(AppBarNavigation.MyEvents.route){
                            MyEventsScreen(navController)
                        }
                        composable(AppNavigation.CreateEvent.route){
                            CreateEventScreen(navController)
                        }
                    }
                }
            }
        }
    }
//}