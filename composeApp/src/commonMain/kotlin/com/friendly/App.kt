package com.friendly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.friendly.di.initKoin
import com.friendly.layouts.bars.HomeScreenNavBar
import com.friendly.layouts.bars.HomeScreenTopBar
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme

@Composable
fun App() {
    initKoin()
    FriendlyAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.secondary
        ) {
            val navController = rememberNavController()
/*            val currentBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry.value?.destination?.route
            println(currentRoute)
            Scaffold(
                topBar = {
                    if(currentRoute == AppNavigation.Discover.route)
                    {
                        HomeScreenTopBar(navController)
                    }
                },
                bottomBar = {
                    if(currentRoute == AppNavigation.Discover.route)
                    {
                        HomeScreenNavBar(navController)
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary,
            ) {innerPadding->*/
                AppNavHost(
                    Modifier,//.padding(innerPadding),
                    navController,
                    AppNavigation.HomeScreen.route
                )
            }
        }
    }
//}