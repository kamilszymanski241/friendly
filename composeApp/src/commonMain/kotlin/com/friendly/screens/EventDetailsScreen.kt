package com.friendly.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
                        Text(
                            text = eventDetails.value!!.title,
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = eventDetails.value!!.locationText,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.startTime,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.endTime,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.startDate,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.endDate,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.description ?: "",
                            fontSize = 15.sp
                        )
                        StaticMapComponent(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            eventDetails.value!!.locationCoordinates,
                            15f
                        )
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
                                }
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
                                }
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