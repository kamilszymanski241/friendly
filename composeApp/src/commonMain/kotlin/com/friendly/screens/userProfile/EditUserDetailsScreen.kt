package com.friendly.screens.userProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.DatePickerDialog
import com.friendly.components.TextInputDialog
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.helpers.DateTimeHelper
import com.friendly.helpers.SelectableDatesTypes
import com.friendly.models.Gender
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.userProfile.EditUserDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserDetailsScreen(navController: NavController, viewModel: EditUserDetailsViewModel = koinViewModel()) {

    val userDetails by viewModel.userDetails.collectAsState()
    val errorMessage = viewModel.errorMessage.collectAsState()

    var showNameInputDialog by remember { mutableStateOf(false) }
    var showSurnameInputDialog by remember { mutableStateOf(false) }
    var showDOBInputDialog by remember { mutableStateOf(false) }
    var showDescriptionInputDialog by remember { mutableStateOf(false) }
    var showGenderSelectDialog by remember { mutableStateOf(false) }

    FriendlyAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopBarWithBackButtonAndTitle(navController, "Edit User Details")
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) { innerPadding ->
                if (userDetails != null) {
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.clickable { showNameInputDialog = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Name",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails?.name ?: "",
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.clickable { showSurnameInputDialog = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Surname",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails?.surname ?: "",
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.clickable { showDOBInputDialog = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Date of birth",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails?.dateOfBirth ?: "",
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.clickable { showGenderSelectDialog = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Gender",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails?.gender.toString(),
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.clickable { showDescriptionInputDialog = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Description",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails?.description  ?: "",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }
                        if (showNameInputDialog) {
                            TextInputDialog(
                                title = "Name",
                                initialValue = userDetails?.name  ?: "",
                                onConfirm = {
                                    viewModel.changeName(it)
                                    showNameInputDialog = false
                                },
                                allowSpaces = false,
                                minLength = 1,
                                maxLength = 20,
                                maxInputLines = 1,
                                onDismiss = { showNameInputDialog = false })
                        }
                        if (showSurnameInputDialog) {
                            TextInputDialog(
                                title = "Surname",
                                initialValue = userDetails?.surname  ?: "",
                                onConfirm = {
                                    viewModel.changeSurname(it)
                                    showSurnameInputDialog = false
                                },
                                allowSpaces = false,
                                minLength = 1,
                                maxLength = 20,
                                maxInputLines = 1,
                                onDismiss = { showSurnameInputDialog = false })
                        }
                        if (showDOBInputDialog) {
                            DatePickerDialog(
                                onDateSelected = { dateInMillis ->
                                if (dateInMillis != null) {
                                    viewModel.changeDateOfBirth(
                                        DateTimeHelper.convertMillisToDate(dateInMillis)
                                    )
                                } else {
                                    viewModel.setErrorMessage("Something went wrong")
                                }
                                showDOBInputDialog = false
                            },
                                onDismiss = { showDOBInputDialog = false },
                                selectableDatesType = SelectableDatesTypes.Past
                            )
                        }
                        if (showGenderSelectDialog) {
                            var selectedGender by remember { mutableStateOf(userDetails?.gender) }
                            var expanded by remember { mutableStateOf(false) }
                            BasicAlertDialog(onDismissRequest = { showGenderSelectDialog = false }) {
                                Column(
                                    modifier = Modifier.padding(20.dp)
                                        .clip(RoundedCornerShape(16.dp)).background(Color.White)
                                        .padding(20.dp)
                                ) {
                                    ExposedDropdownMenuBox(
                                        expanded = expanded,
                                        onExpandedChange = { expanded = !expanded }
                                    ) {
                                        OutlinedTextField(
                                            label = { Text("Gender") },
                                            readOnly = true,
                                            value = selectedGender.toString(),
                                            onValueChange = {},
                                            modifier = Modifier
                                                .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedLabelColor = Color.Black,
                                                focusedBorderColor = Color.Black
                                            ),
                                        )
                                        ExposedDropdownMenu(
                                            expanded = expanded,
                                            onDismissRequest = { expanded = false },
                                            shape = RoundedCornerShape(16.dp),
                                            containerColor = Color.White,
                                        ) {
                                            for (gender in Gender.entries) {
                                                DropdownMenuItem(
                                                    text = {
                                                        Text(
                                                            gender.name,
                                                            color = Color.Black
                                                        )
                                                    },
                                                    onClick = {
                                                        selectedGender = gender
                                                        expanded = false
                                                    }
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(
                                            onClick = { showGenderSelectDialog = false }
                                        ) {
                                            Text(
                                                text = "Cancel",
                                                color = Color.Red
                                            )
                                        }
                                        TextButton(
                                            onClick = {
                                                val gender = selectedGender
                                                if (gender != null) {
                                                    viewModel.changeGender(gender)
                                                } else {
                                                    viewModel.setErrorMessage("Something went wrong")
                                                }
                                                showGenderSelectDialog = false
                                            }
                                        ) {
                                            Text(
                                                text = "Confirm",
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if (showDescriptionInputDialog) {
                            TextInputDialog(
                                title = "Description",
                                initialValue = userDetails?.description ?: "",
                                onConfirm = {
                                    viewModel.changeDescription(it)
                                    showDescriptionInputDialog = false
                                },
                                allowSpaces = true,
                                minLength = 0,
                                maxLength = 200,
                                maxInputLines = 10,
                                onDismiss = { showDescriptionInputDialog = false })
                        }
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(
                            text = errorMessage.value,
                            color = MaterialTheme.colorScheme.error,
                        )
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
    }
}