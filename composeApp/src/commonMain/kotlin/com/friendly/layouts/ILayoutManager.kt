package com.friendly.layouts

import com.friendly.layouts.bars.BottomBarType
import com.friendly.layouts.bars.TopBarType
import kotlinx.coroutines.flow.Flow

interface ILayoutManager {

   val currentTopBar: Flow<TopBarType>

    val currentBottomBar: Flow<BottomBarType>

    fun setTopBar(content: TopBarType)

    fun setBottomBar(content: BottomBarType)

}