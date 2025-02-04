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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var password1Visible by rememberSaveable { mutableStateOf(false) }
    var password2Visible by rememberSaveable { mutableStateOf(false) }
    val loading = viewModel.loading.collectAsState()
    val localSoftwareKeyboardController =
        LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit){
        viewModel.setErrorMessage("")
    }
    LaunchedEffect(successState.value) {
        if (successState.value) {
            navController.navigate(AppNavigation.HomeScreen.route)
        }
    }
    if (!loading.value) {
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
                        .padding(innerPadding)
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White),
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
                        Spacer(modifier = Modifier.size(20.dp))
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White),
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
                            visualTransformation = if (password1Visible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            trailingIcon = {
                                val image = if (password1Visible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description = if (password1Visible) "Hide password" else "Show password"

                                IconButton(onClick = {password1Visible = !password1Visible}){
                                    Icon(imageVector  = image, description)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White),
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
                            visualTransformation = if (password2Visible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            trailingIcon = {
                                val image = if (password2Visible)
                                    Icons.Filled.Visibility
                                else Icons.Filled.VisibilityOff

                                val description = if (password2Visible) "Hide password" else "Show password"

                                IconButton(onClick = {password2Visible = !password2Visible}){
                                    Icon(imageVector  = image, description)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        errorMessage.value?.let { message ->
                            Text(
                                text = message,
                                color = MaterialTheme.colorScheme.error,
                            )
                        }
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = {
                            if (viewModel.onContinueToProfilePic()) {
                                localSoftwareKeyboardController?.hide()
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