package com.friendly.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.friendly.models.UserDetails
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CreateEventScreen(navController: NavController) {
/*    FriendlyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            val userDetails = UserDetails(userId = 1, name = "Anna", surname = "Nowak", age = 18)
            val userDetailsList = ArrayList<UserDetails>()
            userDetailsList.add(userDetails)
            val title = remember {
                mutableStateOf("")
            }
            val address = remember {
                mutableStateOf("")
            }
            val date = remember {
                mutableStateOf("")
            }
            val description = remember {
                mutableStateOf("")
            }
            val maxParticipants = remember {
                mutableStateOf("")
            }
            Text(
                text = "Create Your Event",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = title.value,
                onValueChange = { title.value = it },
                label = {
                    Text(
                        text = "Title",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = address.value,
                onValueChange = { address.value = it },
                label = {
                    Text(
                        text = "Address",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = maxParticipants.value,
                onValueChange = { maxParticipants.value = it },
                label = {
                    Text(
                        text = "Max Participants",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = date.value,
                onValueChange = { date.value = it },
                label = {
                    Text(
                        text = "Date",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = description.value,
                onValueChange = { description.value = it },
                label = {
                    Text(
                        text = "Description",
                        color = Color.Black
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(modifier = Modifier.height(20.dp))

            val scope = rememberCoroutineScope()
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                ),
                onClick = {
                    var event = Event(
                        eventId = 0,
                        title = title.value,
                        address = address.value,
                        description = description.value,
                        dateTime = date.value,
                        maxParticipants = maxParticipants.value.toInt()
                    )
                    scope.launch {
                        DataServiceHelper().createNewEvent(event)
                        navController.navigate(AppBarNavigation.Discover.route)
                    }
                }
            )
            {
                Text(
                    text = "Create!"
                )
            }
        }
    }*/
}

@Preview
@Composable
fun CreateEventPreview() {
    val navController = rememberNavController()
    CreateEventScreen(navController)

}