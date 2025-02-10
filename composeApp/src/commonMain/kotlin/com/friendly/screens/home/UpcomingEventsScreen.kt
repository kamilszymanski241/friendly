package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.EventSummaryCard
import com.friendly.viewModels.home.UpcomingEventsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpcomingEventsScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: UpcomingEventsScreenViewModel = koinViewModel()){

    val showSignInReminder by viewModel.showSignInReminder.collectAsState(false)
    val events = viewModel.eventsList.collectAsState(null)
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()

    LaunchedEffect(Unit){
        viewModel.initialize()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { viewModel.refresh() },
        state = pullToRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = Color.White,
                color = MaterialTheme.colorScheme.tertiary,
                state = pullToRefreshState
            )
        }
    ) {
       if(showSignInReminder)
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
            ) {
                Row()
                {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                    )
                }
                Row(){
                    Text(
                        text = "Sign in to see your upcoming events",
                        fontSize = 15.sp
                    )
                }
            }
        }
        else{
           if (events.value == null) {
               Column(
                   horizontalAlignment = Alignment.CenterHorizontally,
                   verticalArrangement = Arrangement.Center,
                   modifier = modifier
               ) {
                   CircularProgressIndicator()
               }
           } else {
               val eventsSize = events.value?.size ?: 0
               if (eventsSize ==0) {
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally,
                       verticalArrangement = Arrangement.Center,
                       modifier = modifier
                   ) {
                       Row()
                       {
                           Icon(
                               imageVector = Icons.Default.Search,
                               contentDescription = "",
                               modifier = Modifier
                                   .size(100.dp)
                           )
                       }
                       Row(){
                           Text(
                               text = "Find interesting events on discover page!",
                               fontSize = 15.sp
                           )
                       }
                   }
               }
               else {
                   LazyColumn(
                       modifier = modifier.then(Modifier.padding(top = 10.dp))
                   ) {
                       (events.value ?: emptyMap()).forEach { (date, events) ->
                           item{
                               Row(
                                   modifier = Modifier.fillMaxWidth(),
                                   verticalAlignment = Alignment.CenterVertically,
                                   horizontalArrangement = Arrangement.Center
                               ) {
                                   Icon(
                                       Icons.Filled.Event,
                                       "",
                                   )
                                   Text(
                                       text = " $date",
                                       fontSize = 22.sp
                                   )
                               }
                           }
                           items(events) { event ->
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
}