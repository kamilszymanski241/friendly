package com.friendly.screens.signInSignUp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.helpers.DateTimeHelper.Companion.getCurrentDateAsString
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.signInSignUp.SignUpViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FillUserDetailsScreen(navController: NavController, viewModel: SignUpViewModel) {
    FriendlyAppTheme {
        val name = viewModel.name.collectAsState(initial = "")
        val surname = viewModel.surname.collectAsState(initial = "")
        val errorMessage = viewModel.errorMessage.collectAsState()
        val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current

        val showDOBDatePicker = remember { mutableStateOf(false) }
        val selectedDOB = viewModel.dateOfBirth.collectAsState(getCurrentDateAsString())

        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Enter your details") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) {innerPadding ->
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
                    Spacer(modifier = Modifier.size(30.dp))
                    errorMessage.value?.let { message ->
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(
                        modifier = Modifier,
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        value = name.value,
                        onValueChange = {
                            viewModel.onNameChange(it)
                        },
                        label = {
                            Text(
                                text = "Name",
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
                        value = surname.value,
                        onValueChange = {
                            viewModel.onSurnameChange(it)
                        },
                        label = {
                            Text(
                                text = "Surname",
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                Button(
                    onClick = {
                        localSoftwareKeyboardController?.hide()
                        if (viewModel.onContinueToProfilePic()) {
                            navController.navigate(AppNavigation.UploadProfilePicture.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Continue")
                }
            }
        }
    }
}