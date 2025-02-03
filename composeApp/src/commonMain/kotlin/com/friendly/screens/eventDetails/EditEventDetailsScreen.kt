package com.friendly.screens.eventDetails

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.components.DatePickerDialog
import com.friendly.components.NumberInputDialog
import com.friendly.components.SearchLocationComponent
import com.friendly.components.StaticMapComponent
import com.friendly.components.TextInputDialog
import com.friendly.components.TimePickerDialog
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.helpers.DateTimeHelper.Companion.convertMillisToDate
import com.friendly.helpers.SelectableDatesTypes
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.eventDetails.EditEventDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEventDetailsScreen(eventId: String, navController: NavController) {

    val viewModel: EditEventDetailsViewModel =
        koinViewModel(parameters = { parametersOf(eventId) })
    val event = viewModel.event.collectAsState()
    val updated = viewModel.updated.collectAsState()

    var showTitleInputModal by remember { mutableStateOf(false) }
    var showDateTimeInputModal by remember { mutableStateOf(false) }
    var showDescriptionInputModal by remember { mutableStateOf(false) }
    var selectedStartDate by remember{ mutableStateOf("") }
    var selectedStartTime by remember{ mutableStateOf( "") }
    var selectedEndDate by remember{ mutableStateOf("") }
    var selectedEndTime by remember{ mutableStateOf("") }
    var errorMessage by remember{ mutableStateOf("") }
    val showStartDatePicker = remember { mutableStateOf(false) }
    val showEndDatePicker = remember { mutableStateOf(false) }
    val showStartTimePicker = remember { mutableStateOf(false) }
    val showEndTimePicker = remember { mutableStateOf(false) }
    var showLocationInputModal by remember { mutableStateOf(false) }
    var showMaxParticipantsInputModal by remember { mutableStateOf(false) }
    var isError by remember{mutableStateOf(false)}

    var coordinates = viewModel.selectedLocationCoordinates.collectAsState()

    LaunchedEffect(updated.value) {
        if(updated.value) {
            viewModel.fetchEventDetails()
        }
    }
    LaunchedEffect(event.value){
        if(event.value != null) {
            selectedStartDate = event.value!!.startDate
            selectedStartTime = event.value!!.startTime
            selectedEndDate = event.value!!.endDate
            selectedEndTime = event.value!!.endTime
        }
    }
    LaunchedEffect(coordinates.value){
        viewModel.updateAddressOnCoordinatesChange()
    }
    FriendlyAppTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TopBarWithBackButtonAndTitle(navController, "Edit Event Details")
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) { innerPadding ->
                if (event.value != null) {
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier.clickable { showTitleInputModal = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Title",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = event.value!!.title,
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.clickable { showDescriptionInputModal = true }
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
                                    text = event.value!!.description ?: "",
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
                        Row(
                            modifier = Modifier.clickable { showDateTimeInputModal = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Date And Time",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "From " + event.value!!.startDate +", "+ event.value!!.startTime + " to " + event.value!!.endDate +", " + event.value!!.endTime,
                                    fontSize = (15.sp),
                                    color = Color.White,
                                    textAlign = TextAlign.Justify
                                )
                                HorizontalDivider(
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.clickable { showMaxParticipantsInputModal = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Max participants",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = event.value!!.maxParticipants.toString(),
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
                        Row(
                            modifier = Modifier.clickable { showLocationInputModal = true }
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(15.dp)
                            ) {
                                Text(
                                    text = "Location",
                                    fontSize = (20.sp),
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = event.value!!.locationText,
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
                        if (showTitleInputModal) {
                            TextInputDialog(
                                title = "Title",
                                initialValue = event.value!!.title,
                                onConfirm = {
                                    viewModel.changeTitle(it)
                                    showTitleInputModal = false
                                    viewModel.fetchEventDetails()
                                },
                                allowSpaces = true,
                                minLength = 1,
                                maxLength = 20,
                                maxInputLines = 1,
                                onDismiss = { showTitleInputModal = false })
                        }
                        if (showDescriptionInputModal) {
                            TextInputDialog(
                                title = "Description",
                                initialValue = event.value?.description ?: "",
                                onConfirm = {
                                    viewModel.changeDescription(it)
                                    showDescriptionInputModal = false
                                    viewModel.fetchEventDetails()
                                },
                                allowSpaces = true,
                                minLength = 0,
                                maxLength = 200,
                                maxInputLines = 10,
                                onDismiss = { showDescriptionInputModal = false })
                        }
                        if (showDateTimeInputModal) {
                            BasicAlertDialog(
                                onDismissRequest = {showDateTimeInputModal = false}
                            ) {
                                Column( modifier = Modifier
                                    .padding(15.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White)
                                    .padding(15.dp))
                                {
                                    Row(modifier = Modifier.fillMaxWidth()) {
                                        Spacer(modifier = Modifier.height(30.dp))
                                        Column(modifier = Modifier.weight(1 / 2f)) {
                                            OutlinedTextField(
                                                label = {Text("Start date")},
                                                value = selectedStartDate,
                                                onValueChange = { },
                                                isError = isError,
                                                shape = RoundedCornerShape(16.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedLabelColor = Color.Black,
                                                    focusedBorderColor = Color.Black
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp)
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
                                            OutlinedTextField(
                                                label = {Text("Start time")},
                                                value = selectedStartTime,
                                                onValueChange = { },
                                                isError = isError,
                                                shape = RoundedCornerShape(16.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedLabelColor = Color.Black,
                                                    focusedBorderColor = Color.Black
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp)
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
                                            OutlinedTextField(
                                                label = {Text("End date")},
                                                value = selectedEndDate,
                                                onValueChange = { },
                                                isError = isError,
                                                shape = RoundedCornerShape(16.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedLabelColor = Color.Black,
                                                    focusedBorderColor = Color.Black
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp)
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
                                            OutlinedTextField(
                                                label = {Text("End time")},
                                                value = selectedEndTime,
                                                onValueChange = { },
                                                isError = isError,
                                                shape = RoundedCornerShape(16.dp),
                                                colors = OutlinedTextFieldDefaults.colors(
                                                    focusedLabelColor = Color.Black,
                                                    focusedBorderColor = Color.Black
                                                ),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(5.dp)
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
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                        Text(text = errorMessage, color = Color.Red)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(
                                            onClick = { showDateTimeInputModal = false }
                                        ) {
                                            Text(
                                                text = "Cancel",
                                                color = Color.Red
                                            )
                                        }
                                        TextButton(
                                            onClick = {
                                                if(selectedStartDate == selectedEndDate){
                                                    if(selectedStartTime<selectedEndTime){
                                                        viewModel.changeDateTimes(selectedStartDate, selectedStartTime, selectedEndDate, selectedEndTime)
                                                        showDateTimeInputModal = false
                                                        viewModel.fetchEventDetails()
                                                        isError = false
                                                        errorMessage = ""
                                                    }
                                                }
                                                else if(selectedStartDate < selectedEndDate){
                                                    viewModel.changeDateTimes(selectedStartDate, selectedStartTime, selectedEndDate, selectedEndTime)
                                                    showDateTimeInputModal = false
                                                    viewModel.fetchEventDetails()
                                                    isError = false
                                                    errorMessage = ""
                                                }
                                                else{
                                                    isError = true
                                                    errorMessage = "Event cannot end before start"
                                                }
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
                        if (showStartDatePicker.value) {
                            DatePickerDialog(
                                onDateSelected = { selectedStartDate = convertMillisToDate(it!!) },
                                onDismiss = { showStartDatePicker.value = false },
                                selectableDatesType = SelectableDatesTypes.Future)
                        }
                        if (showEndDatePicker.value) {
                            DatePickerDialog(
                                onDateSelected = { selectedEndDate = convertMillisToDate(it!!) },
                                onDismiss = { showEndDatePicker.value = false },
                                selectableDatesType = SelectableDatesTypes.Future)
                        }
                        if (showStartTimePicker.value) {
                            TimePickerDialog(
                                onDateSelected = { selectedStartTime =it.toString() },
                                onDismiss = { showStartTimePicker.value = false })
                        }
                        if (showEndTimePicker.value) {
                            TimePickerDialog(
                                onDateSelected = { selectedEndTime = it.toString() },
                                onDismiss = { showEndTimePicker.value = false })
                        }
                        if (showMaxParticipantsInputModal){
                            NumberInputDialog(
                                title = "Max participants",
                                minValue = event.value!!.participants!!.size,
                                maxValue = 0,
                                initialValue = event.value!!.maxParticipants,
                                onConfirm = {
                                    viewModel.changeMaxParticipants(it)
                                    showMaxParticipantsInputModal = false
                                    viewModel.fetchEventDetails()
                                },
                                onDismiss = {showMaxParticipantsInputModal = false}
                            )
                        }
                        if(showLocationInputModal) {
                            BasicAlertDialog(
                                onDismissRequest = { showLocationInputModal = false }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .height(480.dp)
                                        .padding(15.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White)
                                        .padding(15.dp),
                                    verticalArrangement = Arrangement.SpaceBetween
                                )
                                {
                                    Row {
                                        Box()
                                        {
                                            SearchLocationComponent(
                                                onLocationSelected = { locationText ->
                                                    viewModel.onLocationTextChange(locationText)
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                leadingIcon = Icons.Default.Search,
                                                textFieldPlaceHolder = "Search for a place"
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .offset(y = 65.dp)
                                            ) {
                                                StaticMapComponent(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height(300.dp)
                                                        .padding(8.dp)
                                                        .clip(RoundedCornerShape(16.dp)),
                                                    coordinates.value, 15f
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(
                                            onClick = { showLocationInputModal = false }
                                        ) {
                                            Text(
                                                text = "Cancel",
                                                color = Color.Red
                                            )
                                        }
                                        TextButton(
                                            onClick = {
                                                viewModel.changeLocation()
                                                showLocationInputModal = false
                                                viewModel.fetchEventDetails()
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