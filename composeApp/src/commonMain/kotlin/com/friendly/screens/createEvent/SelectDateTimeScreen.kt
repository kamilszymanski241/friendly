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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.DatePickerModal
import com.friendly.components.TimePickerModal
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.helpers.DateTimeHelper.Companion.convertMillisToDate
import com.friendly.helpers.DateTimeHelper.Companion.getCurrentDate
import com.friendly.helpers.DateTimeHelper.Companion.getCurrentTime
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.createEvent.SelectDateTimeScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateTimeScreen(navController: NavController, viewModel: SelectDateTimeScreenViewModel = koinViewModel ()) {
    val showStartDatePicker = remember { mutableStateOf(false) }
    val showEndDatePicker = remember { mutableStateOf(false) }
    val selectedStartDate = MutableStateFlow(getCurrentDate())
    val selectedEndDate = MutableStateFlow(getCurrentDate())

    val showStartTimePicker = remember { mutableStateOf(false) }
    val showEndTimePicker = remember { mutableStateOf(false) }
    val selectedStartTime = remember { mutableStateOf(getCurrentTime()) }
    val selectedEndTime = remember { mutableStateOf(getCurrentTime()) }

    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButtonAndTitle(navController, "Date and time") },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) {innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Column (modifier = Modifier.weight(1 / 2f)) {
                        Text(
                            text = "Start",
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        TextField(
                            value = selectedStartDate.value,
                            onValueChange = { },
                            // label = { Text("Start", color = Color.Black) },
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, contentDescription = "Select date")
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .pointerInput(selectedStartDate) {
                                    awaitEachGesture {
                                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                        // in the Initial pass to observe events before the text field consumes them
                                        // in the Main pass.
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
                            value = selectedStartTime.value,
                            onValueChange = { },
                            //label = { Text("Start", color = Color.Black) },
                            trailingIcon = {
                                Icon(Icons.Filled.AccessTime, contentDescription = "Select time")
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .pointerInput(selectedStartTime) {
                                    awaitEachGesture {
                                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                        // in the Initial pass to observe events before the text field consumes them
                                        // in the Main pass.
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
                            value = selectedEndDate.value,
                            onValueChange = { },
                            //label = { Text("End", color = Color.Black) },
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, contentDescription = "Select date")
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .pointerInput(selectedStartDate) {
                                    awaitEachGesture {
                                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                        // in the Initial pass to observe events before the text field consumes them
                                        // in the Main pass.
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
                            value = selectedEndTime.value,
                            onValueChange = { },
                            //label = { Text("End", color = Color.Black) },
                            trailingIcon = {
                                Icon(Icons.Filled.AccessTime, contentDescription = "Select time")
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .pointerInput(selectedEndTime) {
                                    awaitEachGesture {
                                        // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                                        // in the Initial pass to observe events before the text field consumes them
                                        // in the Main pass.
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
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate(AppNavigation.SelectEventLocalization.route)
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
                DatePickerModal(onDateSelected = {selectedStartDate.value = convertMillisToDate(it!!) }, onDismiss = {showStartDatePicker.value = false})
            }
            if (showEndDatePicker.value) {
                DatePickerModal(onDateSelected = {selectedEndDate.value = convertMillisToDate(it!!) }, onDismiss = {showEndDatePicker.value = false})
            }
            if (showStartTimePicker.value) {
                TimePickerModal(onDateSelected = {selectedStartTime.value = it.toString()}, onDismiss = {showStartTimePicker.value = false})
            }
            if (showEndTimePicker.value) {
                TimePickerModal(onDateSelected = {selectedEndTime.value = it.toString()}, onDismiss = {showEndTimePicker.value = false})
            }
        }
    }
}