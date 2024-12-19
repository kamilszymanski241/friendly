package com.friendly.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.AppSettingsScreenViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppSettingsScreen(navController: NavController, viewModel: AppSettingsScreenViewModel = koinViewModel (), layoutManager: ILayoutManager = koinInject()) {

    LaunchedEffect(Unit){
        layoutManager.setBars(TopBarType.WithBackButton,BottomBarType.Empty)
    }
    FriendlyAppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            OutlinedButton(
                onClick = {
                    viewModel.onSignOut()
                    navController.navigate(AppNavigation.Discover.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Red
                )
            ) {
                Text(
                    text = "Sign Out",
                    color = Color.Red,
                    fontSize = 15.sp
                )
            }
        }
    }
}