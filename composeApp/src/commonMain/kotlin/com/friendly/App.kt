package com.friendly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.friendly.di.initKoin
import com.friendly.layouts.AuthLayout
import com.friendly.layouts.MainLayout
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
            val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
            if (
                currentRoute == AppNavigation.SignUp.route ||
                currentRoute == AppNavigation.SignIn.route ||
                currentRoute == AppNavigation.FillUserDetails.route ||
                currentRoute == AppNavigation.UploadProfilePicture.route
                ) {
                AuthLayout(navController)
            }
            else{
                MainLayout(navController)
            }
        }
    }
}