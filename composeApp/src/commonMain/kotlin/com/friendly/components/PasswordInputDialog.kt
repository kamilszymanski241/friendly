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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordInputDialog(
    initialValue: String,
    onConfirm: (String)-> Unit,
    onDismiss: ()-> Unit
){
    var textField1Value by remember{ mutableStateOf(initialValue) }
    var textField2Value by remember{ mutableStateOf(initialValue) }
    var errorMessage by remember{ mutableStateOf("") }
    var isError by remember{ mutableStateOf(false) }
    var password1Visible by rememberSaveable { mutableStateOf(false) }
    var password2Visible by rememberSaveable { mutableStateOf(false) }
    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier.padding(20.dp).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(20.dp)
        ) {
            Text(
                text = "After change, you'll need to sign in again",
                color = Color.Black,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedTextField(
                label = { Text(text = "Enter password") },
                supportingText = { Text(errorMessage, color = Color.Red) },
                value = textField1Value,
                onValueChange = {textField1Value = it },
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                maxLines = 1,
                visualTransformation = if (password1Visible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (password1Visible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (password1Visible) "Hide password" else "Show password"

                    IconButton(onClick = {password1Visible = !password1Visible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            OutlinedTextField(
                label = { Text(text = "Confirm password") },
                supportingText = { Text(errorMessage, color = Color.Red) },
                value = textField2Value,
                onValueChange = {textField2Value = it },
                isError = isError,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                maxLines = 1,
                visualTransformation = if (password2Visible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (password1Visible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (password1Visible) "Hide password" else "Show password"

                    IconButton(onClick = {password2Visible = !password2Visible}){
                        Icon(imageVector  = image, description)
                    }
                }
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
                        if (textField1Value == textField2Value){
                            if(textField1Value.length >= 6) {
                                onConfirm(textField1Value)
                            }
                            else{
                                isError = true
                                errorMessage = "Password is too short"
                            }
                        }
                        else{
                            isError = true
                            errorMessage = "Values must be the same"
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