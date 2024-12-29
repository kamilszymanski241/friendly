package com.friendly.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.TopBarWithBackButton
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.EventDetailsScreenViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EventDetailsScreen(eventId: String, navController: NavController) {
    val viewModel: EventDetailsScreenViewModel =
        koinViewModel(parameters = { parametersOf(eventId) })
    val buttonType = viewModel.buttonType.collectAsState(EventDetailsScreenViewModel.EventDetailsButtonType.PleaseSignIn)
    val eventDetails = viewModel.eventDetails.collectAsState(null)
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButton(navController) },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                if (eventDetails.value != null) {
                    Column {
                        Text(
                            text = eventDetails.value!!.title,
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = eventDetails.value!!.date,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.address,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.city,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.country,
                            fontSize = 15.sp
                        )
                    }
                    if(buttonType.value == EventDetailsScreenViewModel.EventDetailsButtonType.PleaseSignIn) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = Color.White
                            ),
                            onClick = {
                                navController.navigate(AppNavigation.SignIn.route)
                            }
                        ) {
                            Text(
                                text = "Sign in to join!"
                            )
                        }
                    }else if(buttonType.value == EventDetailsScreenViewModel.EventDetailsButtonType.Join)
                    {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = Color.White
                            ),
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
                    else if(buttonType.value == EventDetailsScreenViewModel.EventDetailsButtonType.QuitAndChat) {
                        Row() {
                            Column(
                                modifier = Modifier.weight(1/2f)
                            ) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red,
                                        contentColor = Color.White
                                    ),
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
                            Column(
                                modifier = Modifier.weight(1/2f)
                            ) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary,
                                        contentColor = Color.White
                                    ),
                                    onClick = {
                                        navController.navigate(AppNavigation.HomeScreen.route)
                                        //TODO()
                                    }
                                ) {
                                    Text(
                                        text = "Chat-TODO"
                                    )
                                }
                            }
                        }
                    }
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}