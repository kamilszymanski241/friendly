package com.friendly.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.components.StaticMapComponent
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.defaultEventPicture
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.EventDetailsScreenViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EventDetailsScreen(eventId: String, navController: NavController) {
    val viewModel: EventDetailsScreenViewModel =
        koinViewModel(parameters = { parametersOf(eventId) })
    val buttonType =
        viewModel.buttonType.collectAsState(EventDetailsScreenViewModel.EventDetailsButtonType.PleaseSignIn)
    val isFull = viewModel.isNotFull.collectAsState(false)
    val eventDetails = viewModel.eventDetails.collectAsState(null)
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Event Details") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                if (eventDetails.value != null) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = eventDetails.value!!.eventPictureUrl,
                            contentDescription = "Event Picture",
                            placeholder = painterResource(Res.drawable.defaultEventPicture),
                            fallback = painterResource(Res.drawable.defaultEventPicture),
                            error = painterResource(Res.drawable.defaultEventPicture),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .aspectRatio(16/9f)
                                .clip(RoundedCornerShape(16.dp))
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 100.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .padding(20.dp),
                        ) {
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = eventDetails.value!!.title,
                                fontSize = 30.sp,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Address",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = eventDetails.value!!.locationText,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Start",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Filled.Event,
                                        "",
                                        Modifier.size(15.dp),
                                        tint = Color.Black
                                    )
                                    Text(
                                        text = eventDetails.value!!.startDate,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Icon(
                                        Icons.Filled.Schedule,
                                        "",
                                        Modifier.size(15.dp),
                                        tint = Color.Black
                                    )
                                    Text(
                                        text = eventDetails.value!!.startTime,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "End",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Filled.Event,
                                        "",
                                        Modifier.size(15.dp),
                                        tint = Color.Black
                                    )
                                    Text(
                                        text = eventDetails.value!!.endDate,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Icon(
                                        Icons.Filled.Schedule,
                                        "",
                                        Modifier.size(15.dp),
                                        tint = Color.Black
                                    )
                                    Text(
                                        text = eventDetails.value!!.endTime,
                                        color = Color.Black,
                                        fontSize = 15.sp,
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Description",
                                fontSize = (20.sp),
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = eventDetails.value!!.description ?: "",
                                fontSize = (15.sp),
                                color = Color.Black,
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Participants",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                if (eventDetails.value!!.participants!!.size > 3) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = "Show all",
                                        fontSize = (15.sp),
                                        color = Color.Black
                                    )
                                }
                            }
                            if (eventDetails.value?.participants != null) {
                                for (participant in eventDetails.value!!.participants!!.take(3)) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable(
                                                onClick = {
                                                    navController.navigate("userProfile/${participant.id}")
                                                }
                                            ),
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
                                            color = Color.Black,
                                        )
                                    }

                                }
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            StaticMapComponent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .clip(RoundedCornerShape(16.dp)),
                                eventDetails.value!!.locationCoordinates,
                                15f
                            )
                        }
                    }
                    when (buttonType.value) {
                        EventDetailsScreenViewModel.EventDetailsButtonType.PleaseSignIn -> {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .align(Alignment.BottomCenter),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = Color.White
                                ),
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    navController.navigate(AppNavigation.SignIn.route)
                                },
                                enabled = isFull.value
                            ) {
                                Text(
                                    text = "Sign in to join!"
                                )
                            }
                        }
                        EventDetailsScreenViewModel.EventDetailsButtonType.Edit ->{
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .align(Alignment.BottomCenter),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = Color.White
                                ),
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    viewModel.onEdit()
                                    navController.navigate(AppNavigation.HomeScreen.route)
                                }
                            ) {
                                Text(
                                    text = "Edit"
                                )
                            }
                        }

                        EventDetailsScreenViewModel.EventDetailsButtonType.Join -> {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .align(Alignment.BottomCenter),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary,
                                    contentColor = Color.White
                                ),
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    viewModel.onJoin()
                                    navController.navigate(AppNavigation.HomeScreen.route)
                                },
                                enabled = isFull.value
                            ) {
                                Text(
                                    text = "Join!"
                                )
                            }
                        }

                        EventDetailsScreenViewModel.EventDetailsButtonType.Quit -> {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                                    .align(Alignment.BottomCenter),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ),
                                shape = MaterialTheme.shapes.medium,
                                onClick = {
                                    viewModel.onQuit()
                                    navController.navigate(AppNavigation.HomeScreen.route)
                                }
                            ) {
                                Text(
                                    text = "Quit!"
                                )
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}