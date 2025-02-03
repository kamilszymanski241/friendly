package com.friendly.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.friendly.helpers.SelectableDatesTypes
import com.friendly.helpers.SelectableEventDates

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
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
                    text = "Confirm",
                    color = Color.Black
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "Cancel",
                    color = Color.Red
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