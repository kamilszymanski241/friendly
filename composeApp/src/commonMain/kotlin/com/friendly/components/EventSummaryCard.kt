package com.friendly.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.defaultEventPicture
import com.friendly.models.Event
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventSummaryCard(event: Event, navController: NavController, modifier: Modifier = Modifier) {
    FriendlyAppTheme {
        Card(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(200.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = MaterialTheme.shapes.large,
            onClick = {
                navController.navigate("eventDetails/${event.id}")
            }
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(2 / 5f, true)
                ) {
                    AsyncImage(
                        model = event.eventPictureUrl,
                        contentDescription = "Event Picture",
                        placeholder = painterResource(Res.drawable.defaultEventPicture),
                        fallback = painterResource(Res.drawable.defaultEventPicture),
                        error = painterResource(Res.drawable.defaultEventPicture),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(3 / 5f, true)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .weight(3/5f, true),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            Row() {
                                Text(
                                    text = event.title,
                                    fontSize = 22.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.Event,
                                            "",
                                            modifier.size(15.dp)
                                        )
                                        Text(
                                            text = event.startDate,
                                            fontSize = 15.sp,
                                        )
                                        Spacer(modifier = Modifier.size(5.dp))
                                        Icon(
                                            Icons.Filled.Schedule,
                                            "",
                                            modifier.size(15.dp)
                                        )
                                        Text(
                                            text = event.startTime,
                                            fontSize = 15.sp,
                                        )
                                    }
                                    Spacer(modifier = Modifier.size(5.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.LocationOn,
                                            "",
                                            modifier.size(15.dp)
                                        )
                                        Text(
                                            text = event.locationText,
                                            fontSize = 15.sp,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }

                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .weight(2/5f, true),
                    ) {
                        Column() {
                            val spotsLeft = event.maxParticipants!! - (event.participants?.size ?: 0)
                            Row(){
                                if(spotsLeft > 0) {
                                    Text(
                                        text = spotsLeft.toString(),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = " spots left!",
                                        fontSize = 15.sp
                                    )
                                }
                                else{
                                    Text(
                                        text = "This event is full!",
                                        fontSize = 15.sp,
                                        color = Color.Red
                                    )
                                }

                            }
                            Row() {
                                if(event.participants != null)
                                {
                                    if (event.participants.isNotEmpty()) {
                                        for (participant in event.participants.take(5)) {
                                            AsyncImage(
                                                model = participant.profilePictureUrl,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .size(30.dp)
                                            )
                                        }
                                    } else {
                                        Text(
                                            text = "Be the first one to join!"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}