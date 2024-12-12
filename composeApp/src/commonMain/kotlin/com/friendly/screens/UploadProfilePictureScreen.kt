package com.friendly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.CapturePhoto
import com.friendly.convertBase64ToBitMap
import com.friendly.decodeByteArrayToBitMap
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_black
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.layouts.CameraLayout
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.CollectPictureViewModel
import io.ktor.util.decodeBase64Bytes
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UploadProfilePictureScreen(navController: NavController, viewModel: CollectPictureViewModel = koinViewModel ()) {
    FriendlyAppTheme {
        LaunchedEffect(Unit) {
            val base64Image = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.get<String>("capturedImage")
            base64Image?.let {
                val imageBitmap = convertBase64ToBitMap(base64Image)
                viewModel.setPicture(imageBitmap)
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>("capturedImage")
            }
        }
        val capturedPhoto = viewModel.userProfilePicture.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Step 2/3:",
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            if (capturedPhoto.value == null) {
                Image(
                    painter = painterResource(Res.drawable.friendly_logo_white),
                    contentDescription = "logo",
                    modifier = Modifier.size(100.dp)
                )
            } else {
                Image(
                    bitmap = capturedPhoto.value!!,
                    null
                )
            }
            Button(
                onClick = {
                    navController.navigate(AppNavigation.CapturePhoto.route + "/capturedImage")
                    //isCameraOpen = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = Color.White
                )
            ) {
                Text("Upload")
            }
            Spacer(modifier = Modifier.height(10.dp))
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
        if (capturedPhoto.value == null) {
            Image(
                painter = painterResource(Res.drawable.friendly_logo_white),
                contentDescription = "logo",
                modifier = Modifier.size(100.dp)
            )
        } else {
            Image(
                bitmap = capturedPhoto.value!!,
                null,
                modifier = Modifier.fillMaxSize().rotate(90f),
                contentScale = ContentScale.FillHeight
            )
        }
    }
}
