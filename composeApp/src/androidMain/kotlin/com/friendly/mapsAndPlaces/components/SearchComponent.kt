package com.friendly.mapsAndPlaces.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchComponent(modifier: Modifier = Modifier, viewModel: SearchViewModel = koinViewModel(), onLocationSelected: (String) -> Unit) {

    val query = viewModel.query.collectAsState()
    val suggestions = viewModel.suggestions.collectAsState()

    val context = LocalContext.current

    Column {
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
    val keyboardController = LocalSoftwareKeyboardController.current
    Column {
        Row {
            TextField(
                value = query,
                onValueChange = {
                    onQueryChanged(it)
                    showLocationList = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                placeholder = { Text("Search for a place...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search Icon")
                },
                singleLine = true
            )
        }
        if(showLocationList) {
            Row {
                Popup(
                    onDismissRequest = {
                        showLocationList = false
                    },
                    properties = PopupProperties()
                ) {
                    LazyColumn(
                        modifier = Modifier.background(Color.Transparent).clip(RoundedCornerShape(16.dp)),
                        userScrollEnabled = true,

                        ) {
                        items(suggestions) { suggestion ->
                            Row(
                                modifier = Modifier.fillMaxWidth().background(Color.White),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.Place,
                                    "",
                                    tint = Color.Black
                                )
                                Text(
                                    text = suggestion,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onSuggestionClicked(suggestion)
                                            showLocationList = false
                                            keyboardController?.hide()
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}