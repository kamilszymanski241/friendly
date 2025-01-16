package com.friendly.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.friendly.components.HomeScreenTopBar
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.home.HomeScreenViewModel
import io.github.jan.supabase.auth.status.SessionStatus
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeScreenViewModel = koinViewModel()) {

    val selectedView = rememberSaveable {mutableStateOf(0)}
    val sessionStatus = viewModel.sessionStatus.collectAsState()
    FriendlyAppTheme {
        Scaffold (
            floatingActionButton = {
                if (selectedView.value == 2 && sessionStatus.value != SessionStatus.NotAuthenticated(true) && sessionStatus.value != SessionStatus.NotAuthenticated(false)) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = Color.White
                        ),
                        onClick = {
                            navController.navigate(AppNavigation.CreateEvent.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        }
                    ) {
                        Text("Create!")
                    }
                }
            },
            topBar = { HomeScreenTopBar(navController) },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth()
                        .wrapContentHeight(),
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                ) {
                    NavigationBarItem(
                        selected = selectedView.value == 0,
                        onClick = {
                            selectedView.value = 0
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Celebration,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        label = {
                            Text(
                                text = "Discover",
                                color = Color.White
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                    NavigationBarItem(
                        selected = selectedView.value == 1,
                        onClick = {
                            selectedView.value = 1
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        label = {
                            Text(
                                text = "Upcoming Events",
                                color = Color.White
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                    NavigationBarItem(
                        selected = selectedView.value == 2,
                        onClick = {
                            selectedView.value = 2
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        label = {
                            Text(
                                text = "My Events",
                                color = Color.White
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary
        ){innerPadding->
            when (selectedView.value){
                0 -> {
                    DiscoverScreen(navController, modifier = Modifier.padding(innerPadding).fillMaxSize())
                }
                1 -> {
                    UpcomingEventsScreen(navController, modifier = Modifier.padding(innerPadding).fillMaxSize())
                }
                2 -> {
                    MyEventsScreen(navController, modifier = Modifier.padding(innerPadding).fillMaxSize())
                }
            }
        }
    }
}