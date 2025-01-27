package com.friendly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumberInputDialog(
    title: String,
    initialValue: Int,
    minValue: Int,
    maxValue: Int,
    onConfirm: (Int)->Unit,
    onDismiss: ()->Unit
){
    var textFieldValue by remember{mutableStateOf(initialValue.toString())}
    var errorMessage by remember{mutableStateOf("")}
    var isError by remember{mutableStateOf(false)}

    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier.padding(20.dp).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(20.dp)
        ) {
            OutlinedTextField(
                label = { Text(text = title) },
                supportingText = {Text(errorMessage)},
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = { onDismiss() }
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.Red
                    )
                }
                TextButton(
                    onClick = {
                        var maxParticipantsNumber: Int? = textFieldValue.toIntOrNull()
                        if (maxParticipantsNumber == null) {
                            errorMessage = "Must be a number"
                            isError = true
                        } else {
                            if (maxParticipantsNumber < minValue && minValue != 0) {
                                errorMessage = "Must be bigger than $minValue"
                                isError = true
                            } else if (maxParticipantsNumber > maxValue && maxValue != 0) {
                                errorMessage = "Must be less than $maxValue"
                                isError = true
                            } else {
                                onConfirm(maxParticipantsNumber)
                            }
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