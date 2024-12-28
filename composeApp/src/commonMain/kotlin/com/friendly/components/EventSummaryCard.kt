package com.friendly.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.friendly.App
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.sampleEventPhoto
import com.friendly.models.Event
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

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
            Row() {
                Column(
                    modifier = Modifier.weight(2 / 5f, true)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.sampleEventPhoto),
                        null,
                        contentScale = ContentScale.Crop,
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
                                    Row() {
                                        Text(
                                            text = event.date,
                                            fontSize = 15.sp,
                                        )
                                        Spacer(modifier = Modifier.size(5.dp))
                                        Text(
                                            text = event.time,
                                            fontSize = 15.sp,
                                        )
                                    }
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = event.address + "," + event.city + "," + event.country,
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
                            Row() {
                                if (event.participants!!.isNotEmpty()) {
                                    for (participant in event.participants) {
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