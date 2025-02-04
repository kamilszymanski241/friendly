package com.friendly.screens.eventDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.viewModels.eventDetails.EditEventDetailsViewModel
import com.friendly.viewModels.eventDetails.ShowAllParticipantsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAllParticipantsScreen(eventId: String, navController: NavController) {
    val viewModel: ShowAllParticipantsViewModel =
        koinViewModel(parameters = { parametersOf(eventId) })
    val eventDetails = viewModel.eventDetails.collectAsState(null)
    val isOrganizer = viewModel.isOrganizer.collectAsState(false)
    val isRefreshing = viewModel.isRefreshing.collectAsState(false)
    val pullToRefreshState = rememberPullToRefreshState()
    LaunchedEffect(Unit){
        viewModel.initialize()
    }
    Scaffold(
        topBar = {
            TopBarWithBackButtonAndTitle(navController, "All Participants")
        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        if (eventDetails.value != null) {
            val organizer = eventDetails.value!!.participants?.firstOrNull { it.id == eventDetails.value!!.organizer }
            val participantsWithOrganizerFirst = listOfNotNull(organizer) +
                    eventDetails.value!!.participants!!
                        .filter { it.id != eventDetails.value!!.organizer }
            PullToRefreshBox(
                isRefreshing = isRefreshing.value,
                onRefresh = { viewModel.refresh() },
                state = pullToRefreshState,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = isRefreshing.value,
                        containerColor = Color.White,
                        color = MaterialTheme.colorScheme.tertiary,
                        state = pullToRefreshState
                    )
                }
            ) {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding).padding(start = 15.dp, end = 15.dp) .fillMaxSize()
                ) {
                    items(participantsWithOrganizerFirst) { participant ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = participant.profilePictureUrl,
                                    contentDescription = "Profile pic",
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(35.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    participant.name + " " + participant.surname + ", " + participant.age,
                                    fontSize = (15.sp),
                                    color = Color.White,
                                )
                            }
                            Row {
                                IconButton(
                                    onClick = {
                                        navController.navigate("userProfile/${participant.id}")
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Info,
                                        "",
                                        tint = Color.White
                                    )
                                }
                                if (isOrganizer.value && participant.id != eventDetails.value!!.organizer) {
                                    IconButton(
                                        onClick = {
                                            viewModel.removeParticipant(
                                                participant.id
                                            )
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.Cancel,
                                            "",
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }
                        }
                        if (participant.id == eventDetails.value!!.organizer) {
                            HorizontalDivider(
                                color = Color.White
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                text = "Organizer",
                                fontSize = 15.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
    }
}