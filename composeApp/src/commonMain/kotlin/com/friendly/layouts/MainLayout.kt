package com.friendly.layouts

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.MainNavBar
import com.friendly.layouts.bars.MainTopBar
import com.friendly.layouts.bars.TopBarType
import com.friendly.layouts.bars.TopBarWithBackButton
import com.friendly.layouts.bars.TopBarWithBackEditAndSettingsButton
import com.friendly.navigation.AppNavHost
import com.friendly.navigation.AppNavigation
import com.friendly.themes.FriendlyAppTheme
import org.koin.compose.koinInject

@Composable
fun MainLayout(navController: NavHostController, layoutManager: ILayoutManager = koinInject (), content: @Composable (PaddingValues)->Unit) {
    val currentTopBar by layoutManager.currentTopBar.collectAsState(TopBarType.Main)
    val currentBottomBar by layoutManager.currentBottomBar.collectAsState(BottomBarType.MainNavigation)
    val areBarsReady by layoutManager.areBarsReady.collectAsState(true)
    FriendlyAppTheme {
        if(areBarsReady.not())
        {
            Text(
                text = "LOADING BARS"
            )
        }
        else{
            Scaffold(
                topBar = {
                    when (currentTopBar) {
                        TopBarType.Main -> {
                            MainTopBar(navController)
                        }

                        TopBarType.WithBackButton -> {
                            TopBarWithBackButton(navController)
                        }

                        TopBarType.UserProfile -> {
                            TopBarWithBackEditAndSettingsButton(
                                navController,
                                AppNavigation.Discover,
                                AppNavigation.AppSettings
                            )
                        }
                        TopBarType.Empty -> {}
                    }
                },
                bottomBar = {
                    when (currentBottomBar) {
                        BottomBarType.MainNavigation -> {
                            MainNavBar(navController)
                        }

                        BottomBarType.Empty -> {}
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) { innerPadding ->
                content(innerPadding)
            }
        }
    }
}