package com.friendly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.friendly.di.initKoin
import com.friendly.layouts.MainLayout
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    initKoin()
    FriendlyAppTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            val navController = rememberNavController()
            MainLayout(navController) {padding ->
                AppNavHost(
                    Modifier,
                    navController,
                    AppNavigation.Discover.route
                )
            }
        }
    }
}