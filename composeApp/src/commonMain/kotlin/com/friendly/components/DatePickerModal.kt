package com.friendly.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.friendly.helpers.DateTimeHelper
import com.friendly.helpers.SelectableDatesTypes
import com.friendly.helpers.SelectableEventDates
import kotlinx.datetime.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    selectableDatesType: SelectableDatesTypes
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = SelectableEventDates(selectableDatesType)
    )
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                if(datePickerState.selectedDateMillis != null)
                {
                    onDateSelected(datePickerState.selectedDateMillis)
                    onDismiss()
                }
            }) {
                Text(
                    text = "OK",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Cancel",
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                todayContentColor = Color.Black,
                todayDateBorderColor = MaterialTheme.colorScheme.secondary
            ))
    }
}