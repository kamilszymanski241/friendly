package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.friendly.layouts.bars.HomeScreenNavBar
import com.friendly.layouts.bars.HomeScreenTopBar
import com.friendly.themes.FriendlyAppTheme
import org.koin.compose.koinInject

@Composable
fun UpcomingEventsScreen(navController: NavController){
    FriendlyAppTheme {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "TODO- Upcoming Events"
            )
        }
    }
}