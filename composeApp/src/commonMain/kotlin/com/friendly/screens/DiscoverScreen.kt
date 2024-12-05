package com.friendly.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.friendly.components.EventSummaryCard
import com.friendly.models.Event
import com.friendly.viewModels.DiscoverScreenViewModel
import org.koin.compose.koinInject

@Composable
fun DiscoverScreen(viewModel: DiscoverScreenViewModel = koinInject()) {

    val events = viewModel.eventsList.collectAsState(initial = listOf()).value
    EventsOverview(events, Modifier)
}

@Composable
fun EventsOverview(eventsList: List<Event>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(eventsList) { event ->
            EventSummaryCard(
                event = event,
            )
        }
    }
}