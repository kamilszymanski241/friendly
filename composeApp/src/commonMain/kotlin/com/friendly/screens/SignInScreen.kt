package com.friendly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.layouts.ILayoutManager
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.SignInViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SignInScreen(navController: NavController, viewModel: SignInViewModel = koinViewModel (), layoutManager: ILayoutManager = koinInject()) {
    FriendlyAppTheme {
        val email = viewModel.email.collectAsState(initial = "")
        val password = viewModel.password.collectAsState()
        val errorMessage = viewModel.errorMessage.collectAsState()
        val successState = viewModel.success.collectAsState()
        LaunchedEffect(successState.value) {
            if (successState.value) {
                navController.navigate(AppNavigation.Discover.route)
            }
        }
/*        layoutManager.setTopBar { TopBarWithBackButton(navController) }
        layoutManager.setBottomBar {  }*/
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
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
                text = "Sign in:",
                fontSize = 40.sp
            )
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
            val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
            Button(
                onClick = {
                    localSoftwareKeyboardController?.hide()
                    viewModel.onSignIn()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                )) {
                Text("Sign In")
            }
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(
                onClick = {
                    navController.navigate(AppNavigation.SignUp.route)
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