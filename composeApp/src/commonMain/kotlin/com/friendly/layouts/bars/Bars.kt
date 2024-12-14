package com.friendly.layouts.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.navigation.AppNavigation
import com.friendly.session.UserDetailsStatus
import com.friendly.viewModels.MainTopBarViewModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(navController: NavController, viewModel: MainTopBarViewModel = koinViewModel (), auth: Auth = koinInject()) {
    val user by viewModel.user.collectAsState()
    val userDetails by viewModel.userDetails.collectAsState()
    val userProfilePicture by viewModel.userProfilePicture.collectAsState()
    val sessionStatus by viewModel.sessionStatus.collectAsState()
    val userDetailsStatus by viewModel.userDetailsStatus.collectAsState()
    val userProfilePictureStatus by viewModel.userProfilePictureStatus.collectAsState()
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
                if (user == null) {
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
                } else if (sessionStatus == SessionStatus.Initializing || userDetailsStatus == UserDetailsStatus.Initializing || userProfilePictureStatus == UserDetailsStatus.Initializing) {
                    Text(
                        text = "...",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                } else {
                    TextButton(
                        onClick = {
                            navController.navigate(AppNavigation.UserProfile.route)
                        }
                    ) {
                        if (userDetailsStatus == UserDetailsStatus.Success && userDetailsStatus == UserDetailsStatus.Success) {
                            Image(
                                bitmap = userProfilePicture!!,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                            )
                        } else {//TODO()
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

@Composable
fun MainNavBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth()
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.tertiary,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            selected = currentRoute == AppNavigation.Discover.route,
            onClick = {
                if (currentRoute != AppNavigation.Discover.route) {
                    navController.navigate(AppNavigation.Discover.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            label = {
                Text(
                    text = AppNavigation.Discover.label,
                    color = Color.White
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationBarItem(
            selected = currentRoute == AppNavigation.UpcomingEvents.route,
            onClick = {
                if (currentRoute != AppNavigation.UpcomingEvents.route) {
                    navController.navigate(AppNavigation.UpcomingEvents.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            label = {
                Text(
                    text = AppNavigation.UpcomingEvents.label,
                    color = Color.White
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationBarItem(
            selected = currentRoute == AppNavigation.MyEvents.route,
            onClick = {
                if (currentRoute != AppNavigation.MyEvents.route) {
                    navController.navigate(AppNavigation.MyEvents.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            label = {
                Text(
                    text = AppNavigation.MyEvents.label,
                    color = Color.White
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithBackButton(navController: NavController) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            IconButton(
                onClick = { navController.popBackStack() },
            ) {
                Icon(
                    Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        })
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarWithBackEditAndSettingsButton(navController: NavController,editRoute: AppNavigation, settingsRoute: AppNavigation) {
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
                            onClick = { navController.navigate(editRoute.route) },
                        ) {
                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = { navController.navigate(settingsRoute.route) },
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