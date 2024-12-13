package com.friendly.layouts

import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class LayoutManager: ILayoutManager {

    private val _currentTopBar = MutableStateFlow<TopBarType>(TopBarType.Main)
    override val currentTopBar: Flow<TopBarType> = _currentTopBar

    private val _currentBottomBar = MutableStateFlow<BottomBarType>(BottomBarType.MainNavigation)
    override val currentBottomBar: Flow<BottomBarType> = _currentBottomBar


    override fun setTopBar(content: TopBarType) {
        _currentTopBar.value = content
    }

    override fun setBottomBar(content: BottomBarType) {
        _currentBottomBar.value = content
    }

}