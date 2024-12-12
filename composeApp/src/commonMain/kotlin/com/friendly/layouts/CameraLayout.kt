package com.friendly.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraLayout(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AppNavHost(
            Modifier,
            navController = navController,
            startDestination = AppNavigation.Discover.route
        )
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(25.dp)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}