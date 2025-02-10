package com.friendly.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.navigation.AppNavigation
import com.friendly.viewModels.home.HomeScreenTopBarViewModel
import io.github.jan.supabase.auth.status.SessionStatus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(navController: NavController, viewModel: HomeScreenTopBarViewModel = koinViewModel ()) {
    val userDetails by viewModel.userDetails.collectAsState()
    val sessionStatus by viewModel.sessionStatus.collectAsState()
    LaunchedEffect(sessionStatus){
        when(sessionStatus){
            is SessionStatus.Authenticated->{
                viewModel.fetchUserDetails()
            }
            else->{}
        }
    }
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.friendly_logo_white),
                    contentDescription = "logo",
                    modifier = Modifier.size(100.dp)
                )
                if (sessionStatus is SessionStatus.NotAuthenticated ||
                    sessionStatus is SessionStatus.RefreshFailure) {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        TextButton(
                            onClick = {
                                navController.navigate(AppNavigation.SignIn.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = AppNavigation.SignIn.label,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                navController.navigate(AppNavigation.SignUp.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                }
                            }
                        ) {
                            Text(
                                text = AppNavigation.SignUp.label,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }
                } else if (sessionStatus == SessionStatus.Initializing || userDetails == null) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(end = 12.dp)
                    )
                } else {
                    TextButton(
                        onClick = {
                            navController.navigate("userProfile/${userDetails?.id}")
                        }
                    ) {
                        if (userDetails != null) {
                            AsyncImage(
                                model = userDetails?.profilePictureUrl,
                                contentDescription = "User Profile Picture",
                                modifier = Modifier
                                    .clip(CircleShape)
                            )
                        } else {
                            //TODO()
                            Text(
                                text = "ERROR",
                                color = Color.Red
                            )
                        }
                    }
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBackButtonAndTitle(navController: NavController, title: String) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxSize()
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        })
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarWithBackEditAndSettingsButton(navController: NavController,editRoute: String, settingsRoute: String) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Column(
                ) {
                    Row() {
                        IconButton(
                            onClick = {
                                navController.navigate(route = editRoute)
                            },
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = { navController.navigate(settingsRoute) },
                        ) {
                            Icon(
                                Icons.Default.Settings,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarWithBackEditAndDeleteButton(navController: NavController,editRoute: String, onDelete: ()-> Unit) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                Column(
                ) {
                    Row() {
                        IconButton(
                            onClick = {
                                navController.navigate(route = editRoute)
                            },
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = { onDelete() },
                        ) {
                            Icon(
                                Icons.Default.Cancel,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    )
}