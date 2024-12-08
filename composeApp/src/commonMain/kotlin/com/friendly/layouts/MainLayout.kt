package com.friendly.layouts

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.generated.resources.user_default_profile_picture
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation
import com.friendly.session.UserDetailsStatus
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.MainLayoutViewModel
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainLayout(navController: NavHostController, viewModel: MainLayoutViewModel = koinViewModel()) {
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBar(navController, viewModel) },
            bottomBar = { NavBar(navController) },
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            AppNavHost(
                Modifier.padding(innerPadding),
                navController,
                AppNavigation.Discover.route
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController, viewModel: MainLayoutViewModel, auth: Auth = koinInject()) {
    val user by viewModel.user.collectAsState()
    val userDetails by viewModel.userDetails.collectAsState()
    val userProfilePicture by viewModel.userProfilePicture.collectAsState()
    val sessionStatus by viewModel.sessionStatus.collectAsState()
    val userDetailsStatus by viewModel.userDetailsStatus.collectAsState()
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
                } else if (userDetailsStatus == UserDetailsStatus.Initializing || sessionStatus == SessionStatus.Initializing) {
                    Text(
                        text = "Loading",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                } else {
                    if (userDetailsStatus == UserDetailsStatus.Success) {
                        TextButton(
                            onClick = {
                                navController.navigate(AppNavigation.UserProfile.route)
                            }
                        ) {
                            if(userProfilePicture != null)
                            {
                                Image(
                                    bitmap = userProfilePicture!!,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Black, CircleShape)
                                )
                            }
                            else{
                                Image(
                                    painter = painterResource(Res.drawable.user_default_profile_picture),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Black, CircleShape)
                                )
                            }
                        }
                    } else {
                        Text(
                            text = "ERROR"//TODO()
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun NavBar(navController: NavController) {
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