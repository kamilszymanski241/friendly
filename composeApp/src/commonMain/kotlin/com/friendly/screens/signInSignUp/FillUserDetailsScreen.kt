package com.friendly.screens.signInSignUp

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.components.DatePickerDialog
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.helpers.DateTimeHelper.Companion.convertMillisToDate
import com.friendly.helpers.SelectableDatesTypes
import com.friendly.models.Gender
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.signInSignUp.SignUpViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FillUserDetailsScreen(navController: NavController, viewModel: SignUpViewModel) {
    FriendlyAppTheme {
        val name = viewModel.name.collectAsState(initial = "")
        val surname = viewModel.surname.collectAsState(initial = "")
        val description = viewModel.description.collectAsState(initial= "")
        val selectedDOB = viewModel.dateOfBirth.collectAsState(null)
        val gender = viewModel.gender.collectAsState(null)
        val errorMessage = viewModel.errorMessage.collectAsState()

        val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
        val showDOBDatePicker = remember { mutableStateOf(false) }
        var expandedGenderSelect by remember { mutableStateOf(false) }

        LaunchedEffect(Unit){
            viewModel.setErrorMessage("")
        }

        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Enter your details") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) {innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(innerPadding)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
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
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
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
                    Spacer(modifier = Modifier.size(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(Modifier.weight(1/2f)) {
                            TextField(
                                value = selectedDOB.value ?: "Date Of Birth",
                                onValueChange = { },
                                shape = RoundedCornerShape(16.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .pointerInput(selectedDOB) {
                                        awaitEachGesture {
                                            awaitFirstDown(pass = PointerEventPass.Initial)
                                            val upEvent =
                                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                            if (upEvent != null) {
                                                showDOBDatePicker.value = true
                                            }
                                        }
                                    }
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(Modifier.weight(1/2f)) {
                            ExposedDropdownMenuBox(
                                expanded = expandedGenderSelect,
                                onExpandedChange = { expandedGenderSelect = !expandedGenderSelect }
                            ) {
                                TextField(
                                    readOnly = true,
                                    value = gender.value?.toString() ?: "Gender",
                                    onValueChange = {},
                                    modifier = Modifier
                                        .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White
                                    )
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedGenderSelect,
                                    onDismissRequest = { expandedGenderSelect = false },
                                    shape = RoundedCornerShape(16.dp),
                                    containerColor = Color.White,
                                ) {
                                    Gender.entries.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option.toString()) },
                                            onClick = {
                                                viewModel.onGenderChange(option)
                                                expandedGenderSelect = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        value = description.value,
                        onValueChange = {
                            viewModel.onDescriptionChange(it)
                        },
                        label = {
                            Text(
                                text = "Description",
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
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
                        localSoftwareKeyboardController?.hide()
                        if (viewModel.onContinueToProfilePictureSelection()) {
                            viewModel.setErrorMessage("")
                            navController.navigate(AppNavigation.UploadProfilePicture.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Continue")
                }
                if (showDOBDatePicker.value) {
                    DatePickerDialog(
                        onDateSelected = {
                            if (it != null) {
                                viewModel.onDateOfBirthChange(convertMillisToDate(it))
                            }
                            else{
                                showDOBDatePicker.value = false
                            }
                        },
                        onDismiss = { showDOBDatePicker.value = false },
                        selectableDatesType = SelectableDatesTypes.Past)
                }
            }
        }
    }
}