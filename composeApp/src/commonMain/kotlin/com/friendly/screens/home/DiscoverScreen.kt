package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.outlined.MoodBad
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.friendly.components.EventSummaryCard
import com.friendly.components.SearchLocationComponent
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.PermissionsViewModel
import com.friendly.viewModels.home.DiscoverScreenViewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: DiscoverScreenViewModel = koinViewModel()) {

    val options = listOf(5,10,20,50,100)

    val events = viewModel.eventsList.collectAsState(null)
    val distance = viewModel.distance.collectAsState(10)
    val selectedLocationAddress = viewModel.selectedLocationAddress.collectAsState()
    val tags = viewModel.distance.collectAsState(emptyList<Int>())

    var expandedDistanceSelect by remember { mutableStateOf(false) }

    var showPermissionWasNotGranted by remember {mutableStateOf(false)}

    val factory = rememberPermissionsControllerFactory()
    val controller = remember(factory) {
        factory.createPermissionsController()
    }
    BindEffect(controller)
    val permissionsViewModel = viewModel {
        PermissionsViewModel(controller)
    }
    LaunchedEffect(Unit, permissionsViewModel.state) {
        when (permissionsViewModel.state) {
            PermissionState.Granted -> {
                viewModel.initialize()
            }

            PermissionState.DeniedAlways -> {
                showPermissionWasNotGranted = true
            }

            PermissionState.NotGranted ->{
                showPermissionWasNotGranted = true
            }
            else -> {
                permissionsViewModel.provideOrRequestLocationPermission()
            }
        }
    }
    FriendlyAppTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = modifier
        ) {
            Row(
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Column(Modifier.weight(4/10f)) {
                    ExposedDropdownMenuBox(
                        expanded = expandedDistanceSelect,
                        onExpandedChange = { expandedDistanceSelect = !expandedDistanceSelect }
                    ) {
                        TextField(
                            readOnly = true,
                            value = distance.value.toString() + " km",
                            onValueChange = {},
                            modifier = Modifier
                                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                                .padding(8.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            leadingIcon = {
                                Icon(
                                    Icons.Default.SocialDistance,
                                    ""
                                )
                            }
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDistanceSelect,
                            onDismissRequest = { expandedDistanceSelect = false }
                        ) {
                            options.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text("$option km") },
                                    onClick = {
                                        viewModel.onDistanceChange(option)
                                        expandedDistanceSelect = false
                                    }
                                )
                            }
                        }
                    }
                }
                Column(Modifier.weight(6/10f)) {
                    SearchLocationComponent(
                        modifier = Modifier,
                        onLocationSelected = {
                            viewModel.onLocationChange(it)
                        },
                        initialValue = selectedLocationAddress.value,
                        leadingIcon = Icons.Default.Place
                    )
                }
            }
            if (events.value == null) {
                CircularProgressIndicator()
            } else {
                if(events.value!!.isEmpty()){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = modifier
                    ) {
                        Row()
                        {
                            Icon(
                                imageVector = Icons.Default.SentimentDissatisfied,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }
                        Row(){
                            Text(
                                text = "No events nearby...",
                                fontSize = 15.sp
                            )
                        }
                    }
                }
                else {
                    LazyColumn() {
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
    }
}