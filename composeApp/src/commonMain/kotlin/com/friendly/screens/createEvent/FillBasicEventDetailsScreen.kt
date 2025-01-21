package com.friendly.screens.createEvent

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.CapturePhoto
import com.friendly.PickPhoto
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.defaultEventPicture
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.createEvent.CreateEventViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun FillBasicEventDetailsScreen(navController: NavController, viewModel: CreateEventViewModel) {
    val title = viewModel.title.collectAsState(initial = "")
    val description = viewModel.description.collectAsState(initial = "")
    val capturedPhoto = viewModel.eventPicture.collectAsState()
    var showCamera by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }
    val errorMessage = viewModel.errorMessage.collectAsState("")
    FriendlyAppTheme {
        Scaffold(
            topBar = {
                if (showCamera.not()) {
                TopBarWithBackButtonAndTitle(navController, "Event details")
            } },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            )
            {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Box(
                        modifier = Modifier.size(220.dp)
                    ) {
                        if (capturedPhoto.value == null) {
                            Image(
                                painter = painterResource(Res.drawable.defaultEventPicture),
                                null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        } else {
                            Image(
                                bitmap = capturedPhoto.value!!,
                                null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp))
                            )
                        }
                        var showDropDownMenu by remember { mutableStateOf(false) }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    showDropDownMenu = !showDropDownMenu
                                },
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(Color.White, shape = CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddCircle,
                                    contentDescription = "Add",
                                    tint = MaterialTheme.colorScheme.tertiary,
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showDropDownMenu,
                                onDismissRequest = { showDropDownMenu = false },
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Choose from gallery") },
                                    onClick = {
                                        showPhotoPicker = true
                                        showDropDownMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Take new photo") },
                                    onClick = {
                                        showCamera = true
                                        showDropDownMenu = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        value = title.value,
                        onValueChange = {
                            viewModel.onTitleChange(it)
                        },
                        label = {
                            Text(
                                text = "Title",
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    TextField(
                        modifier = Modifier
                            .padding(start = 30.dp, end = 30.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        value = description.value,
                        onValueChange = {
                            viewModel.onDescriptionChange(it)
                        },
                        label = {
                            Text(
                                text = "Description",
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                errorMessage.value?.let { message ->
                    Text(
                        text = message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Button(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth(),
                    onClick = {
                        viewModel.onConfirmBasicDetails(
                            onSuccess = {
                                navController.navigate(AppNavigation.SelectEventDateTime.route)
                                viewModel.onErrorMessageChange("")
                            },
                            onFailure = {
                                viewModel.onErrorMessageChange("The title must be at least 10 characters")
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                ) {
                    Text("Continue")
                }
            }
            if (showCamera) {
                LaunchedEffect(Unit) {
                    delay(100)
                }
                CapturePhoto(onSelect = { picture ->
                    viewModel.onPictureChange(picture)
                    showCamera = false
                },
                    onClose = {
                        showCamera = false
                    })
            }
            if (showPhotoPicker) {
                LaunchedEffect(Unit) {
                    delay(100)
                }
                PickPhoto(onSelect = { picture ->
                    viewModel.onPictureChange(picture)
                    showPhotoPicker = false
                },
                    onClose = {
                        showPhotoPicker = false
                    })
            }
        }
    }
}


