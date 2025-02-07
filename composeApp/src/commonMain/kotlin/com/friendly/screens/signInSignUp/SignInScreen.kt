package com.friendly.screens.signInSignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.signInSignUp.SignInViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = koinViewModel ()) {
    val email by viewModel.email.collectAsState(initial = "")
    val password by viewModel.password.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val successState by viewModel.success.collectAsState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    FriendlyAppTheme {
        LaunchedEffect(successState) {
            if (successState) {
                navController.navigate(AppNavigation.HomeScreen.route)
            }
        }
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController,"") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(Res.drawable.friendly_logo_white),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(220.dp, 100.dp)
                        .padding(bottom = 30.dp)
                )
                Text(
                    text = "Sign in",
                    fontSize = 40.sp
                )
                errorMessage?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    value = email,
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
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    value = password,
                    onValueChange = {
                        viewModel.onPasswordChange(it)
                    },
                    label = {
                        Text(
                            text = "Password",
                            color = Color.Black
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(imageVector  = image, description)
                        }
                    }
                )
                val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp),
                    onClick = {
                        localSoftwareKeyboardController?.hide()
                        viewModel.onSignIn()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text("Sign In")
                }
                TextButton(
                    onClick = {
                        navController.navigate(AppNavigation.ChooseSignUpMethod.route)
                    }
                ) {
                    Text(
                        text = "No account? Sign up!",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}