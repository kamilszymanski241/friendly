package com.friendly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.friendly.viewModels.SearchLocationViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLocationComponent(
    modifier: Modifier = Modifier,
    viewModel: SearchLocationViewModel = koinViewModel(),
    textFieldPlaceHolder: String= "",
    initialValue: String = "",
    leadingIcon: ImageVector,
    onLocationSelected: (String) -> Unit
) {
    val query = viewModel.query.collectAsState()
    val suggestions = viewModel.suggestions.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(initialValue){
        viewModel.onQueryChanged(initialValue)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier.onFocusChanged { viewModel.locationIsSelected(false) }
    ) {
        TextField(
            value = query.value,
            onValueChange = { newValue ->
                viewModel.onQueryChanged(newValue)
                expanded = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .menuAnchor(MenuAnchorType.PrimaryEditable, true),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            placeholder = { Text(textFieldPlaceHolder, overflow = TextOverflow.Ellipsis, maxLines = 1) },
            leadingIcon = {
                Icon(leadingIcon, contentDescription = "Search Icon")
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Clear,
                    "",
                    modifier = Modifier
                        .clickable{
                            viewModel.onQueryChanged("")
                        }
                )
            },
            singleLine = true
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .background(Color.Transparent)
                .height(250.dp)
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth()
                .background(Color.White)
        ) {
            suggestions.value.forEach { suggestion ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = suggestion,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    onClick = {
                        focusManager.clearFocus()
                        onLocationSelected(suggestion)
                        viewModel.locationIsSelected(true)
                        expanded = false
                        viewModel.onQueryChanged(suggestion)
                        keyboardController?.hide()
                    }
                )
            }
        }
    }
}

