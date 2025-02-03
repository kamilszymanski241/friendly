package com.friendly.screens.userProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.CapturePhoto
import com.friendly.PickPhoto
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.components.TopBarWithBackEditAndSettingsButton
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.defaultUserPicture
import com.friendly.navigation.AppNavigation
import com.friendly.viewModels.userProfile.UserProfileViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(userId: String, navController: NavController) {
    val viewModel: UserProfileViewModel =
        koinViewModel(parameters = { parametersOf(userId) })
    var showCamera by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }
    val userDetails = viewModel.userDetails.collectAsState()
    val isSelfProfile = viewModel.isSelfProfile.collectAsState()
    val showSignInReminder = viewModel.showSignInReminder.collectAsState(false)
    var showDropDownMenu by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullToRefreshState = rememberPullToRefreshState()
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }
    Box {
        Scaffold(
            topBar = {
                if (isSelfProfile.value) {
                    TopBarWithBackEditAndSettingsButton(
                        navController,
                        AppNavigation.EditUserDetails.route,
                        AppNavigation.AppSettings.route
                    )
                } else {
                    TopBarWithBackButtonAndTitle(navController, "")
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            if (showSignInReminder.value) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
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
                    Row() {
                        Text(
                            text = "Sign in to see other's profile",
                            fontSize = 15.sp
                        )
                    }
                }
            } else {
                if (userDetails.value != null) {
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
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize().padding(innerPadding)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(450.dp)
                                        .padding(15.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White)
                                        .padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top,
                                ) {
                                    Spacer(modifier = Modifier.size(50.dp))
                                    Text(
                                        text = userDetails.value!!.name + " " + userDetails.value!!.surname,
                                        fontSize = (30.sp),
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(25.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Age",
                                            fontSize = (20.sp),
                                            color = Color.Black,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = userDetails.value!!.age.toString(),
                                            fontSize = (15.sp),
                                            color = Color.Black
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(15.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = "Gender",
                                            fontSize = (20.sp),
                                            color = Color.Black,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = userDetails.value!!.gender.toString(),
                                            fontSize = (15.sp),
                                            color = Color.Black
                                        )
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
                                        text = userDetails.value!!.description,
                                        fontSize = (15.sp),
                                        color = Color.Black,
                                        textAlign = TextAlign.Justify
                                    )
                                }
                                Box(
                                    modifier = Modifier.offset(y = (-120).dp)
                                        .align(Alignment.TopCenter)
                                ) {
                                    AsyncImage(
                                        model = userDetails.value?.profilePictureUrl,
                                        contentDescription = "User Profile Picture",
                                        placeholder = painterResource(Res.drawable.defaultUserPicture),
                                        fallback = painterResource(Res.drawable.defaultUserPicture),
                                        error = painterResource(Res.drawable.defaultUserPicture),
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(180.dp)
                                    )
                                    if (isSelfProfile.value) {
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
        if (showCamera) {
            LaunchedEffect(Unit) {
                delay(100)
            }
            CapturePhoto(onSelect = { picture ->
                viewModel.changeProfilePicture(picture)
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
                viewModel.changeProfilePicture(picture)
                showPhotoPicker = false
            },
                onClose = {
                    showPhotoPicker = false
                })
        }
    }
}

