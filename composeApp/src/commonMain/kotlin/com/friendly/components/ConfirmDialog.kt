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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    title: String,
    onConfirm: ()-> Unit,
    onDismiss: ()-> Unit
){
    BasicAlertDialog(
        onDismissRequest = onDismiss,
    ){
        Column(
            modifier = Modifier.padding(20.dp).clip(RoundedCornerShape(16.dp)).background(Color.White).padding(20.dp)
        ) {
            Text(
                text=title,
                fontSize = 20.sp,
                color = Color.Black
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
                        onConfirm()
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