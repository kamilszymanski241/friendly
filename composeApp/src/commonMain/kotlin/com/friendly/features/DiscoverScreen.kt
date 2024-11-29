package com.friendly.features

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.friendly.components.EventSummaryCard
import com.friendly.dataServices.DataServiceHelper
import com.friendly.models.Event
import kotlinx.coroutines.launch

@Composable
fun DiscoverScreen() {
    val scope = rememberCoroutineScope()
    var events by remember { mutableStateOf<List<Event>>(emptyList()) }
    LaunchedEffect(Unit) {
        scope.launch {
            events = DataServiceHelper().getEventsFromAPI()
        }
    }
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