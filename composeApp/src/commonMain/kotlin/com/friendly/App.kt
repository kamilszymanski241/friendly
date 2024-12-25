package com.friendly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.friendly.di.initKoin
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
                AppNavHost(
                    Modifier,
                    navController,
                    AppNavigation.HomeScreen.route
                )
            }
        }
    }
//}