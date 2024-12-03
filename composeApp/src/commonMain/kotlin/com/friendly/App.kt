package com.friendly

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.friendly.components.NavBar
import com.friendly.components.TopBar
import com.friendly.di.initKoin
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
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
            val currentDestination =
                navController.currentBackStackEntryAsState()?.value?.destination?.route
            if (currentDestination != AppNavigation.SignUp.route && currentDestination != AppNavigation.SignIn.route) {
                Scaffold(
                    topBar = { TopBar(navController) },
                    bottomBar = { NavBar(navController) },
                    containerColor = MaterialTheme.colorScheme.secondary
                ) { innerPadding ->
                    AppNavHost(
                        Modifier.padding(innerPadding),
                        navController,
                        AppNavigation.Discover.route
                    )
                }
            }
            else{
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                            ),
                            title = {
                        IconButton(

                            onClick = {navController.navigateUp()},
                        ) {
                            Icon(
                                Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    })},
                    containerColor = MaterialTheme.colorScheme.secondary
                ) { innerPadding ->
                    AppNavHost(
                        Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = AppNavigation.Discover.route
                    )
                }
            }
        }
    }
}