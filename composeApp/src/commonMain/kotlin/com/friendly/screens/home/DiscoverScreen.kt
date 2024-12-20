package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.friendly.components.EventSummaryCard
import com.friendly.layouts.bars.HomeScreenNavBar
import com.friendly.layouts.bars.HomeScreenTopBar
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.DiscoverScreenViewModel
import org.koin.compose.koinInject

@Composable
fun DiscoverScreen(navController: NavController, appNavController: NavController, modifier: Modifier = Modifier, viewModel: DiscoverScreenViewModel = viewModel()) {
    val events = viewModel.eventsList.collectAsState(null)
    FriendlyAppTheme {
/*        Scaffold(
            topBar = {HomeScreenTopBar(navController)},
            bottomBar = { HomeScreenNavBar(navController) },
            containerColor = MaterialTheme.colorScheme.secondary
        ) {innerPadding ->*/
            if (events.value == null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        //.padding(innerPadding)
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                       // .padding(innerPadding)
                ) {
                    items(events.value!!) { event ->
                        EventSummaryCard(
                            event = event,
                            modifier = Modifier,
                            navController = navController,
                            appNavController = appNavController
                        )
                    }
                }
            }
        }
    }
//}