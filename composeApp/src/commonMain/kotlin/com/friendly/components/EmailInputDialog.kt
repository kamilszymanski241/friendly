package com.friendly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailInputDialog(
    title: String,
    initialValue: String,
    onConfirm: (String)-> Unit,
    onDismiss: ()-> Unit
){
    var textFieldValue by remember{ mutableStateOf(initialValue) }
    var errorMessage by remember{ mutableStateOf("") }
    var isError by remember{ mutableStateOf(false) }

    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier.padding(20.dp).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(20.dp)
        ) {
            Text(
                text = "After change, you'll need to confirm new email and sign in again",
                color = Color.Black,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                label = { Text(text = title) },
                supportingText = { Text(errorMessage, color = Color.Red) },
                value = textFieldValue,
                onValueChange = {textFieldValue = it },
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                maxLines = 1
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
                        val emailAddressRegex = Regex(
                            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                                    "\\@" +
                                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                                    "(" +
                                    "\\." +
                                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                                    ")+"
                        )
                        if (textFieldValue.matches(emailAddressRegex)){
                            onConfirm(textFieldValue)
                        }
                        else{
                            isError = true
                            errorMessage = "Invalid email"
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