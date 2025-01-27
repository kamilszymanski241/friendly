package com.friendly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputDialog(
    title: String,
    initialValue: String,
    allowSpaces: Boolean,
    minLength: Int,
    maxLength: Int,
    maxInputLines: Int,
    onConfirm: (String)-> Unit,
    onDismiss: ()-> Unit
){
    var textFieldValue by remember{mutableStateOf(initialValue)}
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
                onValueChange = {textFieldValue = it },
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                maxLines = maxInputLines
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
                        if(textFieldValue.length > maxLength){
                            isError = true
                            errorMessage = "Max $maxLength characters"
                        }
                        else if(textFieldValue.length < minLength) {
                            isError = true
                            if(textFieldValue.isEmpty()){
                                errorMessage = "Cannot be empty"
                            }
                            else {
                                errorMessage = "Must be at least $minLength characters"
                            }
                        }
                        else if(!allowSpaces && (" " in textFieldValue)){
                            isError = true
                            errorMessage = "Cannot contain spaces"
                        }
                        else{
                            onConfirm(textFieldValue)
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