package com.friendly.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import com.friendly.themes.FriendlyAppTheme
import com.friendly.viewModels.UserProfileViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(navController: NavController, viewModel: UserProfileViewModel= koinViewModel (), layoutManager: ILayoutManager = koinInject()) {
    layoutManager.setTopBar(TopBarType.UserProfile)
    layoutManager.setBottomBar(BottomBarType.Empty)
    FriendlyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            if (viewModel.userProfilePicture.value != null) {
                Spacer(modifier = Modifier.size(50.dp))
                Image(
                    bitmap = viewModel.userProfilePicture.value!!,
                    null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.size(50.dp))
            Text(
                text = viewModel.userDetails.value!!.name + " " + viewModel.userDetails.value!!.surname,
                fontSize = (30.sp),
                color = Color.White
            )
        }
    }
}