package com.friendly.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoNavBarLayout(navController: NavHostController)
{
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    IconButton(
                        onClick = {navController.popBackStack()},
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                })
        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        AppNavHost(
            Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppNavigation.Discover.route
        )
    }
}