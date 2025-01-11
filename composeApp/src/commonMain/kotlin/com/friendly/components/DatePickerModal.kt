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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        colors = DatePickerDefaults.colors(
            selectedDayContainerColor = MaterialTheme.colorScheme.tertiary
        ),
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
        DatePicker(state = datePickerState)
    }
}