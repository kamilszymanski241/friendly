package com.friendly.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.friendly.components.EventSummaryCard
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.DiscoverScreenViewModel

@Composable
fun DiscoverScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: DiscoverScreenViewModel = viewModel()) {
    println(navController.currentBackStack.value)
    val events = viewModel.eventsList.collectAsState(null)
    FriendlyAppTheme {
        if (events.value == null) {
           Column(
               horizontalAlignment = Alignment.CenterHorizontally,
               modifier = Modifier
                   .fillMaxWidth()
           ) {
               Text(
                   text = "..."
               )
           }
        } else {
            LazyColumn(
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