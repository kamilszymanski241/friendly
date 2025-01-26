package com.friendly.screens.signInSignUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.signInSignUp.SignUpViewModel

@Composable
fun RegisterEmailAndPasswordScreen(navController: NavController, viewModel: SignUpViewModel) {
    val email = viewModel.email.collectAsState(initial = "")
    val password = viewModel.password.collectAsState()
    val passwordRepeat = viewModel.passwordRepeat.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()
    val successState = viewModel.success.collectAsState()
    val localSoftwareKeyboardController =
        LocalSoftwareKeyboardController.current
    LaunchedEffect(successState.value) {
        if (successState.value) {
            navController.navigate(AppNavigation.HomeScreen.route)
        }
    }
    var loading: Boolean by remember { mutableStateOf(false) }
    if (!loading) {
        FriendlyAppTheme {
            Scaffold(
                topBar = { TopBarWithBackButtonAndTitle(navController, "Email and password") },
                bottomBar = {},
                containerColor = MaterialTheme.colorScheme.secondary
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        errorMessage.value?.let { message ->
                            Text(
                                text = message,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                        TextField(
                            modifier = Modifier,
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            value = email.value,
                            onValueChange = {
                                viewModel.onEmailChange(it)
                            },
                            label = {
                                Text(
                                    text = "Email",
                                    color = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(
                            modifier = Modifier,
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            value = password.value,
                            onValueChange = {
                                viewModel.onPasswordChange(it)
                            },
                            label = {
                                Text(
                                    text = "Password",
                                    color = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        TextField(
                            modifier = Modifier,
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            value = passwordRepeat.value,
                            onValueChange = {
                                viewModel.onPasswordRepeatChange(it)
                            },
                            label = {
                                Text(
                                    text = "Repeat password",
                                    color = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        onClick = {
                            if (viewModel.onContinueToProfilePic()) {
                                localSoftwareKeyboardController?.hide()
                                loading = true
                                viewModel.onSignUp()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = Color.White
                        ),
                        shape = MaterialTheme.shapes.medium,
                    ) {
                        Text("Sign up")
                    }
                }
            }
        }
    }
    else{
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CircularProgressIndicator()
        }
    }
}