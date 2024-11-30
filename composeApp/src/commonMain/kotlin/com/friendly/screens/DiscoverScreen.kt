package com.friendly.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.friendly.components.EventSummaryCard
import com.friendly.models.Event
import com.friendly.repositories.EventRepository
import com.friendly.viewModels.EventViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverScreen() {

    val eventViewModel = koinViewModel<EventViewModel>()
    val events = eventViewModel.eventsList.collectAsState(initial = listOf()).value
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