package com.friendly.layouts

import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ILayoutManager {

   val currentTopBar: Flow<TopBarType>

    val currentBottomBar: Flow<BottomBarType>

    val areBarsReady: StateFlow<Boolean>

    suspend fun setBars(topBar: TopBarType, bottomBar: BottomBarType)

    fun changeTopBarSetStatus(isSet: Boolean)


}