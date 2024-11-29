package com.friendly.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.friendly.generated.resources.Res
import com.friendly.generated.resources.friendly_logo_white
import com.friendly.navigation.AppBarNavigation
import com.friendly.themes.FriendlyAppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    FriendlyAppTheme {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            ),
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(Res.drawable.friendly_logo_white),
                        contentDescription = "logo",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        )
    }
}

@Composable
fun NavBar(navController: NavController) {
    FriendlyAppTheme {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .wrapContentHeight(),
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White
        ){
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val items = listOf(
                AppBarNavigation.Discover,
                AppBarNavigation.UpcomingEvents,
                AppBarNavigation.MyEvents
            )
            items.forEach {screen ->
                NavigationBarItem(
                    selected = currentRoute == screen.route,
                    onClick = {
                        if(currentRoute != screen.route){
                            navController.navigate(screen.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = screen.icon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    },
                    label = {
                        Text (
                            text = screen.label,
                            color = Color.White
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun NavBarPreview ()
{
    val navController = rememberNavController()
    NavBar(navController)

}