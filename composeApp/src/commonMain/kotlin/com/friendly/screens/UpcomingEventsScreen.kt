package com.friendly.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.friendly.layouts.ILayoutManager
import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import com.friendly.themes.FriendlyAppTheme
import org.koin.compose.koinInject

@Composable
fun UpcomingEventsScreen(layoutManager: ILayoutManager = koinInject()){
    layoutManager.setTopBar(TopBarType.Main)
    layoutManager.setBottomBar(BottomBarType.MainNavigation)
    FriendlyAppTheme {

    }
}