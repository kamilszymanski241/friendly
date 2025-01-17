package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SocialDistance
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.friendly.components.EventSummaryCard
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.PermissionsViewModel
import com.friendly.viewModels.home.DiscoverScreenViewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: DiscoverScreenViewModel = koinViewModel()) {

    val events = viewModel.eventsList.collectAsState(null)
    val distance = viewModel.distance.collectAsState(15)
    val tags = viewModel.distance.collectAsState(emptyList<Int>())
    val x = viewModel.x.collectAsState("")


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
                OutlinedButton(
                    onClick = {},
                    colors = ButtonDefaults.outlinedButtonColors(
                    )
                ) {
                    Icon(
                        Icons.Default.SocialDistance,
                        ""
                    )
                    Text(
                        text = distance.value.toString() +" km"
                    )
                }
                OutlinedButton(
                    onClick = {}
                ) {
                    Icon(
                        Icons.Default.Tag,
                        ""
                    )
                    Text(
                        text = x.value
                    )
                }
            }
            if (events.value == null) {
                CircularProgressIndicator()
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
}