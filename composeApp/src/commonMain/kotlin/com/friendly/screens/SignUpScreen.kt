package com.friendly.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.SignUpViewModel
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = koinInject () , layoutManager: ILayoutManager = koinInject()) {
    LaunchedEffect(Unit){
        layoutManager.setBars(TopBarType.WithBackButton, BottomBarType.Empty)
    }
    FriendlyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign up with:",
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            OutlinedButton(
                modifier = Modifier
                    .width(400.dp),
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