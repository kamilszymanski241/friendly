package com.friendly.layouts

import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LayoutManager: ILayoutManager {

    private val _currentTopBar = MutableStateFlow<TopBarType>(TopBarType.Main)
    override val currentTopBar: Flow<TopBarType> = _currentTopBar

    private val _currentBottomBar = MutableStateFlow<BottomBarType>(BottomBarType.MainNavigation)
    override val currentBottomBar: Flow<BottomBarType> = _currentBottomBar

    private val _areBarsReady = MutableStateFlow(true)
    override val areBarsReady: StateFlow<Boolean> = _areBarsReady

    override suspend fun setBars(topBar: TopBarType, bottomBar: BottomBarType) {
        changeTopBarSetStatus(false)
        _currentTopBar.emit(topBar)
        _currentBottomBar.emit(bottomBar)
        changeTopBarSetStatus(true)
    }

    override fun changeTopBarSetStatus(isSet: Boolean)
    {
        _areBarsReady.value = isSet
    }
}