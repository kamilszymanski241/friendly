package com.friendly.screens.signInSignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.friendly.components.CapturePhotoModal
import com.friendly.components.PickPhotoModal
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.defaultUserPicture
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.signInSignUp.SignUpViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun UploadProfilePictureScreen(navController: NavController, viewModel: SignUpViewModel) {
    val capturedPhoto = viewModel.userProfilePicture.collectAsState()
    var showCamera by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }
    val errorMessage = viewModel.errorMessage.collectAsState()
    var showDropDownMenu by remember { mutableStateOf(false) }
    LaunchedEffect(Unit){
        viewModel.setErrorMessage("")
    }
    FriendlyAppTheme {
        Scaffold(
            topBar = {
                if (showCamera.not()) {
                    TopBarWithBackButtonAndTitle(navController, "Upload your profile picture")
                }
            },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(innerPadding)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box() {
                        if (capturedPhoto.value == null) {
                            Image(
                                painter = painterResource(Res.drawable.defaultUserPicture),
                                null,
                                modifier = Modifier
                                    .size(250.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            Image(
                                bitmap = capturedPhoto.value!!,
                                null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(250.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
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
                                    .align(Alignment.BottomEnd),
                                shape = RoundedCornerShape(16.dp),
                                containerColor = Color.White,
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
                    errorMessage.value?.let { message ->
                        Text(
                            text = message,
                            color = MaterialTheme.colorScheme.error,
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        if (viewModel.onContinueToProfilePic()) {
                            if(viewModel.onContinueToEmailAndPassword()) {
                                viewModel.setErrorMessage("")
                                navController.navigate(AppNavigation.RegisterEmailAndPassword.route)
                            }
                        }
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
                CapturePhotoModal(onSelect = { picture ->
                    viewModel.onPictureChange(picture)
                    showCamera = false
                    viewModel.setErrorMessage("")
                },
                    onClose = {
                        showCamera = false
                    })
            }
            if (showPhotoPicker) {
                LaunchedEffect(Unit) {
                    delay(100)
                }
                PickPhotoModal(onSelect = { picture ->
                    viewModel.onPictureChange(picture)
                    showPhotoPicker = false
                    viewModel.setErrorMessage("")
                },
                    onClose = {
                        showPhotoPicker = false
                    })
            }
        }
    }
}
