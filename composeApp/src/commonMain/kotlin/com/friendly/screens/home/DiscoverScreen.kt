package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.components.EventSummaryCard
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.DiscoverScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: DiscoverScreenViewModel = koinViewModel()) {
    val events = viewModel.eventsList.collectAsState(null)
    FriendlyAppTheme {
        if (events.value == null) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = modifier
            ) {
                items(events.value!!) { event ->
                    EventSummaryCard(
                        event = event,
                        modifier = Modifier,
                        navController = navController
                    )
                }
            }
        }
    }
}