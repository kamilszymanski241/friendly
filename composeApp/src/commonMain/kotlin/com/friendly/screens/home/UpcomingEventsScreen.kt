package com.friendly.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.components.EventSummaryCard
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.UpcomingEventsScreenViewModel
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
                   AsyncImage(
                       "https://qirfsvkxjiwjnyqasoda.supabase.co/storage/v1/object/public/profilePictures/e352d6b6-d10f-4232-b5e8-49d3971877c2.jpg",
                       "",
                       onError = { println("COIL ERROR") })
               }
           } else {
/*               LazyColumn(
                   modifier = modifier
               ) {
                   items(events.value!!) { event ->
                       EventSummaryCard(
                           event = event,
                           modifier = Modifier,
                           navController = navController
                       )
                   }
               }*/
           }
        }
    }
}