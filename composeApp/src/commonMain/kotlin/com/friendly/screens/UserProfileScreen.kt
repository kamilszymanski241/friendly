package com.friendly.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.components.TopBarWithBackButtonAndTitle
import com.friendly.components.TopBarWithBackEditAndSettingsButton
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun UserProfileScreen(userId: String, navController: NavController) {
    val viewModel: UserProfileViewModel =
        koinViewModel(parameters = { parametersOf(userId) })

    val userDetails = viewModel.userDetails.collectAsState()
    val isSelfProfile = viewModel.isSelfProfile.collectAsState()

    if (userDetails.value != null) {
    Scaffold(
        topBar = {
            if(isSelfProfile.value){
            TopBarWithBackEditAndSettingsButton(
                navController,
                AppNavigation.HomeScreen,
                AppNavigation.AppSettings
            )}
            else{
                TopBarWithBackButtonAndTitle(navController, "")
            }
        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) { innerPadding ->
        FriendlyAppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(450.dp)
                                .padding(15.dp)
                                .align(Alignment.Center)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White)
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                        ) {
                            Spacer(modifier = Modifier.size(50.dp))
                            Text(
                                text = userDetails.value!!.name + " " + userDetails.value!!.surname,
                                fontSize = (30.sp),
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(25.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Age",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails.value!!.age.toString(),
                                    fontSize = (15.sp),
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Gender",
                                    fontSize = (20.sp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = userDetails.value!!.gender.toString(),
                                    fontSize = (15.sp),
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(15.dp))
                            Text(
                                text = "Description",
                                fontSize = (20.sp),
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = userDetails.value!!.description,
                                fontSize = (15.sp),
                                color = Color.Black,
                                textAlign = TextAlign.Justify
                            )
                        }
                        AsyncImage(
                            model = userDetails.value?.profilePictureUrl,
                            contentDescription = "User Profile Picture",
                            modifier = Modifier
                                .offset(y = (-120).dp)
                                .clip(CircleShape)
                                .size(180.dp)
                                .align(Alignment.TopCenter)
                        )
                    }
                }
            }
        }
    }
    else{
        Column(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    }
}