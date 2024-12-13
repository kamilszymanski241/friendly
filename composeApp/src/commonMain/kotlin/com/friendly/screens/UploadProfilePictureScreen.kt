package com.friendly.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.CapturePhoto
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.default
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.TopBarType
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.CollectPictureViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UploadProfilePictureScreen(navController: NavController, viewModel: CollectPictureViewModel = koinViewModel (), layoutManager: ILayoutManager = koinInject ()) {
    layoutManager.setTopBar(TopBarType.WithBackButton)
    layoutManager.setBottomBar(BottomBarType.Empty)
    FriendlyAppTheme {
        val capturedPhoto = viewModel.userProfilePicture.collectAsState()
        var showCamera by remember{ mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Profile picture",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.size(50.dp))
                Box(
                ) {
                    if (capturedPhoto.value == null) {
                        Image(
                            painter = painterResource(Res.drawable.default),
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
                    IconButton(
                        onClick = {
                            showCamera = true
                        },
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.BottomEnd)
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
                }
            }
            Button(
                onClick = {
                    viewModel.onContinue()
                    navController.navigate(AppNavigation.RegisterEmailAndPassword.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                )
            ) {
                Text("Continue")
            }
        }
        if(showCamera) {
            LaunchedEffect(Unit){
                delay(100)
            }
            layoutManager.setTopBar(TopBarType.Empty)
            CapturePhoto(onSelect = { picture ->
                viewModel.setPicture(picture)
                showCamera = false
                layoutManager.setTopBar(TopBarType.WithBackButton)
            },
                onClose = {
                    println("ON CLOSE SIE ZADZIALO LOLOLOLLOL")
                    showCamera = false
                    layoutManager.setTopBar(TopBarType.WithBackButton)
                })
        }
    }
}
