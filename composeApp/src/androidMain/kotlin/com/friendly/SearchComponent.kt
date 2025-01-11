package com.friendly

import SearchViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchComponent(viewModel: SearchViewModel = koinViewModel(), onLocationSelected: (String) -> Unit) {

    val query = viewModel.query.collectAsState()
    val suggestions = viewModel.suggestions.collectAsState()

    val context = LocalContext.current

    Column {
        // Wyświetl Search Bar
        SearchBar(
            query = query.value,
            onQueryChanged = { viewModel.onQueryChanged(it, context) },
            suggestions = suggestions.value,
            onSuggestionClicked = { suggestion ->
                onLocationSelected(suggestion)
            }
        )
    }
}
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    suggestions: List<String>,
    onSuggestionClicked: (String) -> Unit
) {
    var showLocationList by remember { mutableStateOf(false) }
    Column {
        // TextField jako Search Bar
        TextField(
            value = query,
            onValueChange = {
                onQueryChanged(it)
                showLocationList = true},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Search for a place...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            },
            singleLine = true
        )
        if(showLocationList) {
            // Lista sugestii
            LazyColumn(
                modifier = Modifier.background(Color.White)
            ) {
                items(suggestions) { suggestion ->
                    Text(
                        text = suggestion,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSuggestionClicked(suggestion)
                                showLocationList = false}
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}