package com.friendly.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.friendly.components.NavBar
import com.friendly.components.TopBar
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation

@Composable
fun MainLayout(navController: NavHostController)
{
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