package com.friendly.screens.signInSignUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.components.TopBarWithBackButton
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.signInSignUp.SignUpViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun ChooseSignUpMethod(navController: NavController, viewModel: SignUpViewModel = koinInject ()) {
    FriendlyAppTheme {
        Scaffold(
            topBar = { TopBarWithBackButton(navController) },
            bottomBar = {},
            containerColor = MaterialTheme.colorScheme.secondary
        ) {innerPadding->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(Res.drawable.friendly_logo_white),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(220.dp, 100.dp)
                        .padding(bottom = 30.dp)
                )
                Text(
                    text = "Sign up with:",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.height(50.dp))
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    onClick = {
                        navController.navigate(AppNavigation.FillUserDetails.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text("Email")
                }
            }
        }
    }
}