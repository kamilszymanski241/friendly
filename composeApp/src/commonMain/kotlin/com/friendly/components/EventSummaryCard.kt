package com.friendly.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.friendly.App
import com.friendly.models.Event
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EventSummaryCard(event: Pair<Event, List<ImageBitmap>>, navController: NavController, modifier: Modifier = Modifier) {
    FriendlyAppTheme {
        Card(
            modifier = modifier
                .padding(10.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = MaterialTheme.shapes.large,
            onClick = {
                navController.navigate("eventDetails/${event.first.id}")
            }
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier.weight(3 / 5f, true)
                    ) {
                        Text(
                            text = event.first.title,
                            fontSize = 25.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(
                        modifier = Modifier.weight(2 / 5f, false)
                    ) {
                        val max = event.first.maxParticipants.toString()
                        val current = event.second?.size ?: 0
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "personIcon"
                            )
                            Text(
                                text = "$current/$max",
                                fontSize = 25.sp
                            )
                        }
                    }
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        text = event.first.date,
                        fontSize = 15.sp,

                        )
                }
                Row {
                    Text(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                        text = event.first.address,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if(event.second.isNotEmpty())
                {
                    Row {
                        for (userImage in event.second!!) {
                            Image(
                                bitmap = userImage,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(50.dp)
                            )
                        }
                    }
                }
                else{
                    Text(
                        text = "Be the first one to join!"
                    )
                }
            }
        }
    }
}
