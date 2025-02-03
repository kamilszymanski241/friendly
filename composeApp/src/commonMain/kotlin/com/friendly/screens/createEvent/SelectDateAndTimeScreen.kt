package com.friendly.screens.createEvent

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.DatePickerDialog
import com.friendly.components.TimePickerDialog
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.helpers.DateTimeHelper.Companion.convertMillisToDate
import com.friendly.helpers.DateTimeHelper.Companion.getCurrentDateAsString
import com.friendly.helpers.DateTimeHelper.Companion.getCurrentTimeAsString
import com.friendly.helpers.SelectableDatesTypes
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.createEvent.CreateEventViewModel

@Composable
fun SelectDateAndTimeScreen(navController: NavController, viewModel: CreateEventViewModel) {
    val showStartDatePicker = remember { mutableStateOf(false) }
    val showEndDatePicker = remember { mutableStateOf(false) }
    val selectedStartDate = viewModel.startDate.collectAsState(getCurrentDateAsString())
    val selectedEndDate = viewModel.endDate.collectAsState(getCurrentDateAsString())
    val errorMessage = viewModel.errorMessage.collectAsState("")
    val showStartTimePicker = remember { mutableStateOf(false) }
    val showEndTimePicker = remember { mutableStateOf(false) }
    val selectedStartTime = viewModel.startTime.collectAsState(getCurrentTimeAsString())
    val selectedEndTime = viewModel.endTime.collectAsState(getCurrentTimeAsString())

    val maxParticipants = viewModel.maxParticipants.collectAsState("")
    LaunchedEffect(Unit){
        viewModel.onErrorMessageChange("")
    }
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Date and time") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Column {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(30.dp))
                        Column(modifier = Modifier.weight(1 / 2f)) {
                            Text(
                                text = "Start",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            TextField(
                                value = selectedStartDate.value ?: "",
                                onValueChange = { },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = "Select date"
                                    )
                                },
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
                                    .padding(10.dp)
                                    .pointerInput(selectedStartDate) {
                                        awaitEachGesture {
                                            awaitFirstDown(pass = PointerEventPass.Initial)
                                            val upEvent =
                                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                            if (upEvent != null) {
                                                showStartDatePicker.value = true
                                            }
                                        }
                                    }
                            )
                            TextField(
                                value = selectedStartTime.value ?: "",
                                onValueChange = { },
                                trailingIcon = {
                                    Icon(
                                        Icons.Filled.AccessTime,
                                        contentDescription = "Select time"
                                    )
                                },
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
                                    .padding(10.dp)
                                    .pointerInput(selectedStartTime) {
                                        awaitEachGesture {
                                            awaitFirstDown(pass = PointerEventPass.Initial)
                                            val upEvent =
                                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                            if (upEvent != null) {
                                                showStartTimePicker.value = true
                                            }
                                        }
                                    }
                            )
                        }
                        Column(modifier = Modifier.weight(1 / 2f)) {
                            Text(
                                text = "End",
                                textAlign = TextAlign.Center,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                            TextField(
                                value = selectedEndDate.value ?: "",
                                onValueChange = { },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = "Select date"
                                    )
                                },
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
                                    .padding(10.dp)
                                    .pointerInput(selectedStartDate) {
                                        awaitEachGesture {
                                            awaitFirstDown(pass = PointerEventPass.Initial)
                                            val upEvent =
                                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                            if (upEvent != null) {
                                                showEndDatePicker.value = true
                                            }
                                        }
                                    }
                            )
                            TextField(
                                value = selectedEndTime.value ?: "",
                                onValueChange = { },
                                trailingIcon = {
                                    Icon(
                                        Icons.Filled.AccessTime,
                                        contentDescription = "Select time"
                                    )
                                },
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
                                    .padding(10.dp)
                                    .pointerInput(selectedEndTime) {
                                        awaitEachGesture {
                                            awaitFirstDown(pass = PointerEventPass.Initial)
                                            val upEvent =
                                                waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                            if (upEvent != null) {
                                                showEndTimePicker.value = true
                                            }
                                        }
                                    }
                            )
                        }
                    }
                    Row(
                    ) {
                        TextField(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            value = maxParticipants.value,
                            onValueChange = {
                                viewModel.onMaxParticipantsChange(it)
                            },
                            label = {
                                Text(
                                    text = "Max Participants",
                                    color = Color.Black
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    errorMessage.value?.let { message ->
                        Text(
                            modifier = Modifier.padding(),
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.onConfirmMoreDetails(
                            onSuccess = {
                                viewModel.onErrorMessageChange("")
                                navController.navigate(AppNavigation.SelectEventLocation.route)
                            },
                            onFailure = {
                                viewModel.onErrorMessageChange("Please enter correct date and maximum number of participants")
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Continue")
                }
            }
            if (showStartDatePicker.value) {
                DatePickerDialog(
                    onDateSelected = { viewModel.onStartDateChange(convertMillisToDate(it!!)) },
                    onDismiss = { showStartDatePicker.value = false },
                    selectableDatesType = SelectableDatesTypes.Future)
            }
            if (showEndDatePicker.value) {
                DatePickerDialog(
                    onDateSelected = { viewModel.onEndDateChange(convertMillisToDate(it!!)) },
                    onDismiss = { showEndDatePicker.value = false },
                    selectableDatesType = SelectableDatesTypes.Future)
            }
            if (showStartTimePicker.value) {
                TimePickerDialog(
                    onDateSelected = { viewModel.onStartTimeChange(it.toString()) },
                    onDismiss = { showStartTimePicker.value = false })
            }
            if (showEndTimePicker.value) {
                TimePickerDialog(
                    onDateSelected = { viewModel.onEndTimeChange(it.toString()) },
                    onDismiss = { showEndTimePicker.value = false })
            }
        }
    }
}