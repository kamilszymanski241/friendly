package com.friendly.screens.home

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.EventSummaryCard
import com.friendly.helpers.DateTimeHelper
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.home.UpcomingEventsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UpcomingEventsScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: UpcomingEventsScreenViewModel = koinViewModel()){

    val showSignInReminder by viewModel.showSignInReminder.collectAsState(false)
    val events = viewModel.eventsList.collectAsState(null)

    FriendlyAppTheme {
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
           } else if (events.value!!.isEmpty()) {
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
                   events.value!!.forEach { (date, events) ->
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