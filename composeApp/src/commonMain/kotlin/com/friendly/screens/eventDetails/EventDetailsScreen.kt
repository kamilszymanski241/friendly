package com.friendly.screens.eventDetails

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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.friendly.CapturePhoto
import com.friendly.PickPhoto
import com.friendly.components.StaticMapComponent
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.components.TopBarWithBackEditAndSettingsButton
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.defaultEventPicture
import com.friendly.navigation.AppNavigation
import com.friendly.viewModels.eventDetails.EventDetailsScreenViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailsScreen(eventId: String, navController: NavController) {
    val viewModel: EventDetailsScreenViewModel =
        koinViewModel(parameters = { parametersOf(eventId) })
    val buttonType =
        viewModel.buttonType.collectAsState(EventDetailsScreenViewModel.EventDetailsButtonType.PleaseSignIn)
    var showCamera by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }
    val isViewedByOrganizer = viewModel.isViewedByOrganizer.collectAsState(false)
    val isFull = viewModel.isNotFull.collectAsState(false)
    val eventDetails = viewModel.eventDetails.collectAsState(null)
    var showDropDownMenu by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
    Box() {
        Scaffold(
            topBar = {
                if (isViewedByOrganizer.value) {
                    TopBarWithBackEditAndSettingsButton(
                        navController,
                        editRoute = "editEventDetails/$eventId",
                        settingsRoute = AppNavigation.EventSettings.route
                    )
                } else {
                    TopBarWithBackButtonAndTitle(navController, "Event Details")
                }
            },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding).fillMaxSize()
            ) {
                if (eventDetails.value != null) {
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
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
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
                                        .aspectRatio(16 / 9f)
                                        .clip(RoundedCornerShape(16.dp))
                                )
                                if (isViewedByOrganizer.value) {
                                    IconButton(
                                        onClick = {
                                            showDropDownMenu = !showDropDownMenu
                                        },
                                        modifier = Modifier
                                            .size(50.dp)
                                            .background(Color.White, shape = CircleShape)
                                            .align(Alignment.BottomEnd)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddCircle,
                                            contentDescription = "Add",
                                            tint = MaterialTheme.colorScheme.tertiary,
                                            modifier = Modifier
                                                .size(50.dp)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = showDropDownMenu,
                                        onDismissRequest = { showDropDownMenu = false },
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd),
                                        shape = RoundedCornerShape(16.dp),
                                        containerColor = Color.White,
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Choose from gallery") },
                                            onClick = {
                                                showPhotoPicker = true
                                                showDropDownMenu = false
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Take new photo") },
                                            onClick = {
                                                showCamera = true
                                                showDropDownMenu = false
                                            }
                                        )
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
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
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Participants",
                                            fontSize = (20.sp),
                                            color = Color.Black,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Row( verticalAlignment = Alignment.CenterVertically) {
                                            Icon(
                                                Icons.Filled.Person,
                                                "",
                                                Modifier.size(15.dp),
                                                tint = Color.Black
                                            )
                                            Text(
                                                text = (eventDetails.value!!.participants?.size
                                                    ?: 0).toString() + "/" + eventDetails.value!!.maxParticipants.toString(),
                                                fontSize = (15.sp),
                                                color = Color.Black,
                                            )
                                        }
                                    }
                                }
                                if (eventDetails.value?.participants != null) {
                                    val organizer = eventDetails.value!!.participants!!.firstOrNull { it.id == eventDetails.value!!.organizer }
                                    val participantsWithOrganizerFirst = listOfNotNull(organizer) +
                                            eventDetails.value!!.participants!!
                                                .filter { it.id != eventDetails.value!!.organizer }
                                                .take(3)
                                    for (participant in participantsWithOrganizerFirst.take(3)) {
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
                                                    color = Color.Black,
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
                                                        tint = Color.Black
                                                    )
                                                }
                                                if (isViewedByOrganizer.value && participant.id != eventDetails.value!!.organizer) {
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
                                        if(participant.id == eventDetails.value!!.organizer){
                                            HorizontalDivider(
                                                color = Color.Black
                                            )
                                            Text(
                                               modifier = Modifier.fillMaxWidth(),
                                                textAlign = TextAlign.Center,
                                                text = "Organizer",
                                                fontSize = 8.sp,
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                                Row(
                                    modifier= Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (eventDetails.value!!.participants!!.size > 3) {
                                        TextButton(
                                            onClick = {  navController.navigate("showAllParticipants/${eventId}") },
                                        ) {
                                            Text(
                                                text = "Show all",
                                                fontSize = (15.sp),
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(25.dp))
                                Text(
                                    text = "Address",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = eventDetails.value!!.locationText,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                StaticMapComponent(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                    eventDetails.value!!.locationCoordinates,
                                    15f
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                                when (buttonType.value) {
                                    EventDetailsScreenViewModel.EventDetailsButtonType.PleaseSignIn -> {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
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

                                    EventDetailsScreenViewModel.EventDetailsButtonType.None -> {
                                    }

                                    EventDetailsScreenViewModel.EventDetailsButtonType.Join -> {
                                        Button(
                                            modifier = Modifier
                                                .fillMaxWidth(),
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
                                                .fillMaxWidth(),
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
        if (showCamera) {
            LaunchedEffect(Unit) {
                delay(100)
            }
            CapturePhoto(onSelect = { picture ->
                viewModel.changeEventPicture(picture)
                showCamera = false
            },
                onClose = {
                    showCamera = false
                })
        }
        if (showPhotoPicker) {
            LaunchedEffect(Unit) {
                delay(100)
            }
            PickPhoto(onSelect = { picture ->
                viewModel.changeEventPicture(picture)
                showPhotoPicker = false
            },
                onClose = {
                    showPhotoPicker = false
                })
        }
    }
}