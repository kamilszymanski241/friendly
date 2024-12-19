package com.friendly.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.friendly.viewModels.EventDetailsScreenViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EventDetailsScreen(eventId: String, navController: NavController, layoutManager: ILayoutManager = koinInject ()) {

    LaunchedEffect(Unit){
        layoutManager.setBars(TopBarType.WithBackButton,BottomBarType.Empty)
    }

    val viewModel: EventDetailsScreenViewModel =
        koinViewModel(parameters = { parametersOf(eventId) })

    val eventDetails = viewModel.eventDetails.collectAsState(null)

    LaunchedEffect(Unit) {
        viewModel.loadEvent()
    }

    FriendlyAppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (eventDetails.value != null) {
                    Column {
                        Text(
                            text = eventDetails.value!!.title,
                            fontSize = 30.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = eventDetails.value!!.date,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.address,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.city,
                            fontSize = 15.sp
                        )
                        Text(
                            text = eventDetails.value!!.country,
                            fontSize = 15.sp
                        )
                    }
                Button(
                    modifier = Modifier
                        .width(300.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    onClick = {
                        viewModel.onJoin()
                        navController.navigate(AppNavigation.Discover.route)
                    }
                ){
                    Text(
                        text = "Join!"
                    )
                }
            } else {
                Text(
                    text = "..."
                )
            }
        }
    }
}