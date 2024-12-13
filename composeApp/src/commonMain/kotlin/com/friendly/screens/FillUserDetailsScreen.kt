package com.friendly.screens

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.TopBarType
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.FillUserDetailsViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FillUserDetailsScreen(navController: NavController, viewModel: FillUserDetailsViewModel = koinViewModel (), layoutManager: ILayoutManager = koinInject())
{
    FriendlyAppTheme {
        val name = viewModel.name.collectAsState(initial = "")
        val surname = viewModel.surname.collectAsState(initial = "")
        val errorMessage = viewModel.errorMessage.collectAsState()
        layoutManager.setTopBar(TopBarType.WithBackButton)
        layoutManager.setBottomBar(BottomBarType.Empty)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Your details",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.size(50.dp))
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
            val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
            Button(
                onClick = {
                    localSoftwareKeyboardController?.hide()
                    viewModel.onContinue()
                    navController.navigate(AppNavigation.UploadProfilePicture.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                )) {
                Text("Continue")
            }
        }
    }
}