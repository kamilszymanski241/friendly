package com.friendly.screens.userProfile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.friendly.components.EmailInputDialog
import com.friendly.components.PasswordInputDialog
import com.friendly.components.TextInputDialog
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.managers.SessionManager
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.PermissionsViewModel
import com.friendly.viewModels.userProfile.AppSettingsScreenViewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent

@Composable
fun AppSettingsScreen(navController: NavController, viewModel: AppSettingsScreenViewModel = koinViewModel ()) {

    val email = viewModel.email.collectAsState()
    val emailResponseError = viewModel.emailResponseError.collectAsState()
    val passwordResponseError = viewModel.passwordResponseError.collectAsState()
    val emailChangeSuccess = viewModel.emailChangeSuccess.collectAsState()
    val loading = viewModel.loading.collectAsState()
    var showEmailInputModal by remember { mutableStateOf(false) }
    var showPasswordInputModal by remember { mutableStateOf(false) }
    LaunchedEffect(emailChangeSuccess.value) {
        if (emailChangeSuccess.value) {
            navController.navigate(AppNavigation.HomeScreen.route)
        }
    }
    FriendlyAppTheme {
        if (!loading.value) {
            Scaffold(
                topBar = { TopBarWithBackButtonAndTitle(navController, "Settings") },
                bottomBar = {},
                containerColor = MaterialTheme.colorScheme.secondary
            ) { innerPadding ->
                Box() {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column() {
                            Row(
                                modifier = Modifier.clickable { showEmailInputModal = true }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(
                                            text = "Email ",
                                            fontSize = (20.sp),
                                            color = Color.White,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = emailResponseError.value,
                                            color = Color.Red
                                        )
                                    }
                                    Text(
                                        text = email.value,
                                        fontSize = (15.sp),
                                        color = Color.White,
                                        textAlign = TextAlign.Justify
                                    )
                                    HorizontalDivider(
                                    )
                                }
                            }
                            Row(
                                modifier = Modifier.clickable { showPasswordInputModal = true }
                                    .fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(15.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        Text(
                                            text = "Password ",
                                            fontSize = (20.sp),
                                            color = Color.White,
                                            textAlign = TextAlign.Start,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = passwordResponseError.value,
                                            color = Color.Red
                                        )
                                    }
                                    Text(
                                        text = "******",
                                        fontSize = (15.sp),
                                        color = Color.White,
                                        textAlign = TextAlign.Justify
                                    )
                                    HorizontalDivider(
                                    )
                                }
                            }
                        }
                        Button(
                            onClick = {
                                viewModel.onSignOut()
                                navController.navigate(AppNavigation.HomeScreen.route)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.Red
                            ),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Text(
                                text = "Sign Out",
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                    }
                    if (showEmailInputModal) {
                        EmailInputDialog(
                            title = "Email",
                            initialValue = email.value,
                            onConfirm = {
                                viewModel.onEmailChange(it)
                                showEmailInputModal = false
                            },
                            onDismiss = { showEmailInputModal = false })
                    }
                    if (showPasswordInputModal) {
                        PasswordInputDialog(
                            initialValue = "",
                            onConfirm = {
                                viewModel.onPasswordChange(it)
                                showPasswordInputModal = false
                            },
                            onDismiss = { showPasswordInputModal = false })
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